package space.beka.newcodialgram.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import space.beka.newcodialgram.R
import space.beka.newcodialgram.adapter.MyPostAdapter
import space.beka.newcodialgram.databinding.FragmentHomeBinding
import space.beka.newcodialgram.models.MyPosts

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        firebaseFirestore=FirebaseFirestore.getInstance()
        firebaseFirestore.collection("posts")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val list = ArrayList<MyPosts>()
                    val result = it.result
                    for (snapshot in result) {
                        val myPosts = snapshot.toObject(MyPosts::class.java)
                        list.add(myPosts)
                    }
                    binding.viewPagerPosts.adapter= MyPostAdapter(list)
                }
            }


        binding.bottomMenu.setOnItemSelectedListener {
            if (it.itemId == R.id.menu_add) {
                val addFragment = AddFragment()
                addFragment.show(childFragmentManager.beginTransaction(), "")

            }
            true
        }
        return binding.root
    }


}