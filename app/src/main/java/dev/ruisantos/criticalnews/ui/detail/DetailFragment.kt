package dev.ruisantos.criticalnews.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.ruisantos.criticalnews.R
import dev.ruisantos.criticalnews.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private val mArticle get() = args.myArticle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            Glide.with(this@DetailFragment)
                .load(mArticle.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(ivCover)

            tvTitle.text = mArticle.title
            tvDescription.text = mArticle.description
            tvContent.text = mArticle.content
        }
    }

}