package com.example.tugasbesar_mbanking_18.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasbesar_mbanking_18.databinding.FragmentDataFaqBinding
import androidx.core.view.isVisible
import com.example.tugasbesar_mbanking_18.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
class DataFaqFragment : Fragment() {
    private var _binding: FragmentDataFaqBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val listFaq = ArrayList<FaqData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataFaqBinding.inflate(inflater,
            container, false)
        return binding.root
        getDataFaq()
    }

    override fun onStart() {
        super.onStart()
        getDataFaq()
    }

    private fun getDataFaq() {
        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager= LinearLayoutManager(context)
        val bundle = arguments
        val cari = bundle?.getString(/* key = */ "cari")
        binding.progressBar.visibility
        RClient.instances.getDataFaq(cari).enqueue(object :
            Callback<ResponseDataFaq> {
            override fun onResponse(
                call: Call<ResponseDataFaq>,
                response: Response<ResponseDataFaq>
            ){
                if (response.isSuccessful){
                    listFaq.clear()
                    response.body()?.let {
                        listFaq.addAll(it.data) }
                    val adapter =
                        FaqAdapter(listFaq, requireContext())
                    binding.rvData.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.progressBar.isVisible = false
                }
            }
            override fun onFailure(call: Call<ResponseDataFaq>, t:
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