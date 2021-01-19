package com.example.bezpiecznik.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bezpiecznik.R
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ketiovv/Bezpiecznik"))
            startActivity(intent)
        }

        tvPolsl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.polsl.pl/Strony/Witamy.aspx"))
            startActivity(intent)
        }

        tvKL.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/konradluberapolsl"))
            startActivity(intent)
        }

        tvWK.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ketiovv"))
            startActivity(intent)
        }

        tvRD.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/curiosis"))
            startActivity(intent)
        }
    }
}