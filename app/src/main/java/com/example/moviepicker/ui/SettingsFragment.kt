package com.example.moviepicker.ui

import android.app.Activity
import android.content.res.XmlResourceParser
import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.Config
import com.example.moviepicker.R
import com.example.moviepicker.databinding.FragmentSettingsBinding
import org.xmlpull.v1.XmlPullParser
import java.util.Locale

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    val languagesList = arrayListOf<String>()
    val idList = arrayListOf<String>()

    override fun onResume() {
        super.onResume()

        readXML()

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languagesList)

        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.setOnItemClickListener(AdapterView.OnItemClickListener() { adapterView: AdapterView<*>, view2: View, pos: Int, id: Long ->
            Log.d("SettingsFragment", pos.toString())
            setLocaleLanguage(idList[pos])
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLocaleLanguage(id: String) {
        Log.d("SettingsFragment", "id: $id")
        if (resources.configuration.locales[0] == Locale(id)) {
            return
        }
        resources.configuration.setLocale(Locale(id))
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        requireActivity().recreate()
    }

    private fun readXML() {
        val xmlPullParser = resources.getXml(R.xml.languages)
        while (xmlPullParser.eventType != XmlPullParser.END_DOCUMENT) {
            when (xmlPullParser.eventType) {
                XmlPullParser.START_TAG -> readTag(xmlPullParser)
            }
            xmlPullParser.next()
        }
    }

    private fun readTag(xmlPullParser: XmlPullParser) {
        when (xmlPullParser.name) {
            "language" -> {
                idList.add(xmlPullParser.getAttributeValue(0))
                languagesList.add(xmlPullParser.getAttributeValue(1))
            }
        }
    }

}