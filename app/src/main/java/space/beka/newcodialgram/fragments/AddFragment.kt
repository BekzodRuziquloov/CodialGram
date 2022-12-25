package space.beka.newcodialgram.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import space.beka.newcodialgram.databinding.FragmentAddBinding
import space.beka.newcodialgram.models.MyPosts
import space.beka.newcodialgram.utils.MySharedPrefarance

class AddFragment : DialogFragment() {
    private lateinit var binding: FragmentAddBinding
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var firebaseFireStore: FirebaseFirestore
    var fileLocation: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)
        MySharedPrefarance.init(binding.root.context)
        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFireStore = FirebaseFirestore.getInstance()
        reference = firebaseStorage.getReference("files")


        binding.btnSave.setOnClickListener {

            if (fileLocation != null && binding.edtDesc.text.isNotBlank()) {
                binding.progressAdded.visibility = View.VISIBLE

                val myPosts = MyPosts(
                    binding.edtDesc.text.toString(),
                    0,
                    MySharedPrefarance.user.userName,
                    true,
                    fileLocation.toString()
                )
                val m = System.currentTimeMillis()
                reference.child(m.toString())
                    .putFile(fileLocation!!)
                    .addOnSuccessListener {
                        it.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                            myPosts.fileLocation = it.toString()
                            firebaseFireStore.collection("posts").add(myPosts)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Ma'lumotlar saqlandi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.progressAdded.visibility = View.GONE
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Ma'lumotlar saqlanmadi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }

                    }.addOnFailureListener {
                        Toast.makeText(context, "File yuklanmadi!!", Toast.LENGTH_SHORT).show()
                    }

            } else {
                Toast.makeText(context, "Ma'lumotlar toliq emas!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageAdd.setOnClickListener {
            addImage()
        }
        binding.videoAdd.setOnClickListener {
            addVideo()
        }
        return binding.root
    }

    fun addVideo() {
        getFileContent.launch("video/*")
        isImage = false
    }

    fun addImage() {
        getFileContent.launch("image/*")

    }

    var isImage = true
    private val getFileContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                fileLocation = uri
                if (isImage) {
                    binding.imageAdd.setImageURI(uri)

                } else {
                    binding.videoAdd.setVideoURI(uri)
                    binding.videoAdd.start()
                }

            } else {
                Toast.makeText(context, "File tanlanmadi", Toast.LENGTH_SHORT).show()
            }

        }
}