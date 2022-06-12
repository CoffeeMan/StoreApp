package com.epam.grocerystoreapp.presentation.screens.home

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentLoyaltyCardItemDetailsBinding
import com.epam.grocerystoreapp.presentation.utils.Const
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class LoyaltyCardItemFragment : Fragment(R.layout.fragment_loyalty_card_item_details) {

    private lateinit var binding: FragmentLoyaltyCardItemDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoyaltyCardItemDetailsBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        arguments?.let { bundle ->
            bundle.getString(Const.USER_ID_KEY)?.let { userId ->
                binding.previewImageView.setImageBitmap(getQrCodeBitmap(userId))
            }

            binding.titleTextView.setText(R.string.home_screen_loyalty_card_title)
            binding.descriptionTextView.setText(R.string.home_screen_loyalty_card_description)
        }

        binding.closeImageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getQrCodeBitmap(userId: String): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(userId, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}