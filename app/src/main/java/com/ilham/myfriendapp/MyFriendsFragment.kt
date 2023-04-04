package com.ilham.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFriendsFragment : Fragment() {
    private var fabAddFriend : FloatingActionButton? = null
    private var listMyFriends: RecyclerView? = null
    //lateinit var listTeman : MutableList<MyFriend>
    private var listTeman: List<MyFriend>? = null

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friend_fragment,
            container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }
    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }
    private fun initView() {
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        listMyFriends= activity?.findViewById(R.id.listMyfriends)

        fabAddFriend?.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }
        //simulasiDataTeman()
        //tampilTeman()
        ambilDataTeman()
    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(requireActivity()) { r -> listTeman = r
            when {
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        }
    }

    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun simulasiDataTeman() {
        listTeman = ArrayList()
        //listTeman.add(MyFriend("Muhammad", "Laki-laki", "ade@gmail.com", "085719004268", "Wonosobo"))
        //listTeman.add(MyFriend("Arul", "Laki-laki", "rifaldi@gmail.com", "081213416171", "Papua"))
    }

    private fun tampilTeman() {
        listMyFriends?.layoutManager = LinearLayoutManager(activity)
        listMyFriends?.adapter = MyFriendAdapter(requireActivity(),
        listTeman!! as ArrayList<MyFriend>
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        fabAddFriend = null
        listMyFriends = null
    }
}