package com.example.tugasbesar_mbanking_18.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesar_mbanking_18.databinding.FragmentDataMemoBinding
import androidx.core.view.isVisible
import com.example.tugasbesar_mbanking_18.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class DataMemoFragment : Fragment() {
    private var _binding: FragmentDataMemoBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val listMemo = ArrayList<MemoData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataMemoBinding.inflate(inflater,
            container, false)
        return binding.root
        getDataMemo()
    }

    override fun onStart() {
        super.onStart()
        getDataMemo()
    }

    private fun getDataMemo() {
        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager= LinearLayoutManager(context)
        val bundle = arguments
        val cari = bundle?.getString(/* key = */ "cari")
        binding.progressBar.visibility
        RClient.instances.getData(cari).enqueue(object :
            Callback<ResponseDataMemo> {
            override fun onResponse(
                call: Call<ResponseDataMemo>,
                response: Response<ResponseDataMemo>
            ){
                if (response.isSuccessful){
                    listMemo.clear()
                    response.body()?.let {
                        listMemo.addAll(it.data) }
                    val adapter =
                        MemoAdapter(listMemo, requireContext())
                    binding.rvData.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }
            }
            override fun onFailure(call: Call<ResponseDataMemo>, t:
            Throwable) {
            }
        }
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}