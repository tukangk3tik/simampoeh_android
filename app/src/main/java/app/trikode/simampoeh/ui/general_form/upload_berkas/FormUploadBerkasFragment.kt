package app.trikode.simampoeh.ui.general_form.upload_berkas

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.trikode.simampoeh.R
import app.trikode.simampoeh.core.ui.ViewModelFactory
import app.trikode.simampoeh.databinding.FragmentGeneralFormUploadBerkasBinding
import app.trikode.simampoeh.databinding.ProgressLayoutDarkBinding
import app.trikode.simampoeh.domain.model.upload_berkas.BerkasResponse
import app.trikode.simampoeh.ui.general_form.FormViewModel
import app.trikode.simampoeh.ui.utils.adapter.UploadBerkasListAdapter
import app.trikode.simampoeh.utils.click_listener.upload_berkas.UploadBerkasClickListener
import app.trikode.simampoeh.utils.general.EventLiveData
import com.example.medancapilpelaporan.data.source.remote.network.ApiResponse
import com.example.medancapilpelaporan.utils.general.HandlerLiveData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 *  This form using for all where needed
 *  Created by Felix Serang, 21 September 2021
 */
class FormUploadBerkasFragment : Fragment(), View.OnClickListener, UploadBerkasClickListener {

    private var _binding: FragmentGeneralFormUploadBerkasBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressLayoutDarkBinding: ProgressLayoutDarkBinding
    private lateinit var formViewModel: FormViewModel
    private lateinit var uploadBerkasViewModel: UploadBerkasViewModel

    private lateinit var SELECTED_BERKAS: BerkasResponse
    private lateinit var nextFragment: Fragment

    private var TARGET_DIR = ""
    private var SELECTED_POSITION_BERKAS: Int = 0
    private var idJenisLayanan: Int = 0

    private val adapter = UploadBerkasListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGeneralFormUploadBerkasBinding.inflate(inflater, container, false)
        progressLayoutDarkBinding = binding.progressLayout

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)

        val factory = ViewModelFactory.getInstance(view.context)
        uploadBerkasViewModel = ViewModelProvider(requireActivity(), factory)[UploadBerkasViewModel::class.java]

        adapter.listener = this
        binding.rvBerkas.layoutManager = LinearLayoutManager(view.context)
        binding.rvBerkas.adapter = adapter

        //d("ID_JENIS", uploadBerkasViewModel.getLayananId())
        //uploadBerkasViewModel.setListBerkas()
        uploadBerkasViewModel.listBerkas.observe(viewLifecycleOwner, berkasObserver)
        uploadBerkasViewModel.uploadResultHandler.observe(viewLifecycleOwner, uploadBerkasObserver)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.btnBack.id -> parentFragmentManager.popBackStack()
            binding.btnNext.id -> {
                val mKonfirmasiFragment = nextFragment
                val mFragmentManager = parentFragmentManager

                mFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    replace(
                        R.id.container,
                        mKonfirmasiFragment,
                        FormUploadBerkasFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onBerkasItemClicked(view: View, berkas: BerkasResponse, position: Int) {
        SELECTED_BERKAS = berkas
        SELECTED_POSITION_BERKAS = position
        openUploadSelector()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun openUploadSelector() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alert.setTitle("Pilih sumber gambar")
        alert.setPositiveButton("Kamera") { _, _ ->
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val values = ContentValues(1)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val fileUri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            TARGET_DIR = fileUri.toString()
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            openCameraResult.launch(takePhotoIntent)

        }

        alert.setNegativeButton("Media") { _, _ ->
            if (checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                !=
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    200
                )
                chooseImageGallery()
            } else {
                chooseImageGallery()
            }
        }
        alert.show()
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        openStorageResult.launch(intent)
    }

    private val openCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val cursor = requireActivity().contentResolver.query(
                Uri.parse(TARGET_DIR),
                Array(1) { MediaStore.Images.ImageColumns.DATA },
                null, null, null
            )
            cursor?.moveToFirst()
            val photoPath = cursor?.getString(0)
            cursor?.close()

            if (photoPath != null) {
                val file = File(photoPath)
                //val uri = Uri.fromFile(file)
                uploadFile(file)
            }
        }
    }

    private val openStorageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val targetDir = result.data?.data

            if (targetDir != null){
                val cursor = requireActivity().contentResolver.query(
                    Uri.parse(targetDir.toString()),
                    Array(1) { MediaStore.Images.ImageColumns.DATA },
                    null, null, null
                )

                cursor?.moveToFirst()
                val photoPath = cursor?.getString(0)
                cursor?.close()

                if (photoPath != null) {
                    val file = File(photoPath)
                    uploadFile(file)
                }
            }
        }
    }

    private val uploadBerkasObserver = Observer<EventLiveData<HandlerLiveData>> {
        it.getContentIfNotHandled().let { handler ->
            showUploadLoading(false)
            showLoading(false)
            if (handler != null) {
                if (handler.responseResult > 0) {
                    adapter.notifyItemChanged(SELECTED_POSITION_BERKAS)
                } else {
                    Toast.makeText(requireContext(), handler.responseMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val berkasObserver = Observer<ApiResponse<ArrayList<BerkasResponse>>> { response ->

        when (response) {
            is ApiResponse.Success -> {
                response.data?.let {
                    if (it.size > 0){
                        adapter.setBerkas(it)
                    }
                }
                showLoading(false)
            }
            is ApiResponse.Error -> {
                response.message?.let {
                    //Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                showLoading(false)
            }
            is ApiResponse.Loading -> {
                showLoading(true)
            }
        }
    }

    private fun uploadFile(file: File) {
        showUploadLoading(true)
        try {
            val fileBody: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val bodyPart = MultipartBody.Part.createFormData("file", file.name, fileBody);
            val fileName = RequestBody.create(("text/plain").toMediaTypeOrNull(), file.name);
            val type = RequestBody.create(("text/plain").toMediaTypeOrNull(), SELECTED_BERKAS.id.toString());
            val request = RequestBody.create(("text/plain").toMediaTypeOrNull(), "partial_upload");

            uploadBerkasViewModel.uploadBerkas(bodyPart, fileName, type, request, SELECTED_POSITION_BERKAS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showUploadLoading(state: Boolean) {
        if (state) {
            binding.rvBerkas.isEnabled = false
            progressLayoutDarkBinding.root.visibility = View.VISIBLE
        } else {
            binding.rvBerkas.isEnabled = true
            progressLayoutDarkBinding.root.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvBerkas.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.rvBerkas.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    fun setNextFragment(nFragment: Fragment) {
        nextFragment = nFragment
    }

    fun setViewModel(viewModel: FormViewModel) {
        formViewModel = viewModel
    }

    fun setIdJenisLayanan(id: Int) {
        idJenisLayanan = id
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormUploadBerkasFragment()
    }

}