package com.rinatvasilev.expandablerecyclerview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.RadioButton
import com.rinatvasilev.expandablerecyclerview.affectingotherchilds.AffectingOtherChildsFragment
import com.rinatvasilev.expandablerecyclerview.nodes.NodesFragment
import com.rinatvasilev.expandablerecyclerview.onlyoneopened.OnlyOneOpenedFragment
import com.rinatvasilev.expandablerecyclerview.selectable.SelectableFragment
import com.rinatvasilev.expandablerecyclerview.simple.SimpleFragment

class MainActivity : AppCompatActivity() {

    private lateinit var simple: RadioButton
    private lateinit var selectable: RadioButton
    private lateinit var onlyOneOpened: RadioButton
    private lateinit var affectingOtherChilds: RadioButton
    private lateinit var nodes: RadioButton

    private lateinit var radioList: List<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simple = findViewById(R.id.simple)
        selectable = findViewById(R.id.selectable)
        onlyOneOpened = findViewById(R.id.only_one_opened)
        affectingOtherChilds = findViewById(R.id.affecting_other_childs)
        nodes = findViewById(R.id.nodes)

        radioList = arrayListOf(
            simple,
            selectable,
            onlyOneOpened,
            affectingOtherChilds,
            nodes
        )

        simple.setOnClickListener {
            resetAllRadioButtons()
            simple.isChecked = true
            openFragment(SIMPLE)
        }

        selectable.setOnClickListener {
            resetAllRadioButtons()
            selectable.isChecked = true
            openFragment(SELECTABLE)
        }

        onlyOneOpened.setOnClickListener {
            resetAllRadioButtons()
            onlyOneOpened.isChecked = true
            openFragment(ONLY_ONE_OPENED)
        }

        affectingOtherChilds.setOnClickListener {
            resetAllRadioButtons()
            affectingOtherChilds.isChecked = true
            openFragment(AFFECTING_OTHER_CHILDS)
        }

        nodes.setOnClickListener {
            resetAllRadioButtons()
            nodes.isChecked = true
            openFragment(NODES)
        }
    }

    private fun openFragment(key: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, getFragment(key))
            .commit()
    }

    private fun getFragment(key: String): Fragment {
        return when (key) {
            SIMPLE -> SimpleFragment.getInstance()
            SELECTABLE -> SelectableFragment.getInstance()
            ONLY_ONE_OPENED -> OnlyOneOpenedFragment.getInstance()
            AFFECTING_OTHER_CHILDS -> AffectingOtherChildsFragment.getInstance()
            NODES -> NodesFragment.getInstance()
            else -> SimpleFragment.getInstance()
        }
    }

    private fun resetAllRadioButtons() {
        radioList.forEach { r -> r.isChecked = false }
    }

    companion object {
        private const val SIMPLE = "SIMPLE"
        private const val SELECTABLE = "SELECTABLE"
        private const val ONLY_ONE_OPENED = "ONLY_ONE_OPENED"
        private const val AFFECTING_OTHER_CHILDS = "AFFECTING_OTHER_CHILDS"
        private const val NODES = "NODES"
    }
}
