package com.pmdm.imcapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {
    private var isMale: Boolean = true
    private var weight: Int = 50
    private var age: Int = 18


    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var rsHeight: RangeSlider
    private lateinit var tvHeight: TextView
    private lateinit var textWeight: TextView
    private lateinit var textAge: TextView
    private lateinit var btnRemoveWeight: FloatingActionButton
    private lateinit var btnAddWeight: FloatingActionButton
    private lateinit var btnRemoveAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var btncalcular: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        rsHeight = findViewById(R.id.rsHeight)
        tvHeight = findViewById(R.id.tvHeight)
        textWeight = findViewById(R.id.textWeight)
        textAge = findViewById(R.id.textAge)
        btnRemoveWeight = findViewById(R.id.btnRemoveWeight)
        btnAddWeight = findViewById(R.id.btnAddWeight)
        btnRemoveAge = findViewById(R.id.btnRemoveAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        btncalcular = findViewById(R.id.btncalcular)

    }

    private fun initListeners() {
        viewMale.setOnClickListener() {
            isMale = true
            setGenderColor()
        }
        viewFemale.setOnClickListener() {
            isMale = false
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            //tvHeight.text = value.toString()
            tvHeight.text = DecimalFormat("#.##").format(value)
        }
        btnAddWeight.setOnClickListener {
            weight += 1
            setWeight()
        }
        btnRemoveWeight.setOnClickListener {
            weight -= 1
            setWeight()
        }
        btnAddAge.setOnClickListener {
            age += 1
            setAge()
        }
        btnRemoveAge.setOnClickListener {
            age -= 1
            setAge()
        }
        btncalcular.setOnClickListener {
            calculateIMC()
        }

    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMale))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMale))
    }

    private fun getBackgroundColor(isComponentSelected: Boolean): Int {
        val colorReference = if (isComponentSelected) {
            R.color.bg_comp_sel
        } else {
            R.color.bg_comp
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI() {
        setGenderColor()
        setAge()
        setWeight()
    }

    private fun setWeight() {
        textWeight.text = weight.toString()
    }

    private fun setAge() {
        textAge.text = age.toString()
    }

    private fun calculateIMC() {
        var peso: Double = textWeight.text.toString().toDouble()
        var altura = tvHeight.text.toString().toDouble()
        var resultado: Double
        var res:String
        altura = (altura / 100)
        altura *= altura
        resultado = peso / (altura)
        res = DecimalFormat("#.##").format(resultado)
        var descripcion: String = ""
        var titulo: String = ""


        when (resultado) {
            in 0.0..18.5 -> {
                descripcion = getString(R.string.TextInfrapeso)
                titulo = getString(R.string.NumInfrapeso)

            }

            in 18.6..24.9 -> {
                descripcion = getString(R.string.TextNormal)
                titulo = getString(R.string.NumPesoNormal)

            }

            in 25.00..29.9 -> {
                descripcion = getString(R.string.TextSobrepeso)
                titulo = getString(R.string.NumSobrepeso)

            }

            in 30.00..Double.MAX_VALUE -> {
                descripcion = getString(R.string.TextObesidad)
                titulo = getString(R.string.NumObesidad)

            }

            else -> {
            }
        }
        val intentA = Intent(this, ImcResultadoActivity::class.java)
        intentA.putExtra("Título", titulo)
        intentA.putExtra("Descripción", descripcion)
        intentA.putExtra("Resultado", res)
        startActivity(intentA)
    }
}