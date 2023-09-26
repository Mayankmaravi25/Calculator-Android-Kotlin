package com.example.calculator


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var lastNumeric= false
    var stateError=false
    var lastDot=false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun equal(view: View) {
        OnEqualAcrion()

        binding.data.text =  binding.result.text.toString().drop(1)
    }
    fun OperaterAction(view: View) {

        if(!stateError && lastNumeric)
        {
            binding.data.append((view as Button).text)
            lastDot  = false
            lastNumeric = false
            OnEqualAcrion()
        }


    }
    fun backspace(view: View) {

        binding.data.text = binding.data.text.toString().dropLast(1)

        try {

            val lastchar = binding.data.text.toString().last()

            if(lastchar.isDigit())
            {
                OnEqualAcrion()
            }
        }
        catch (e : Exception)
        {
            binding.result.text = ""
            binding.result.visibility =  View.GONE

            Log.e("last char error ", e.toString())
        }


    }
    fun Allclear(view: View) {

        binding.data.text= ""

        binding.result.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.result.visibility= View.GONE
    }
    fun digit(view: View) {

        if(stateError){
            binding.data.text=(view as Button).text
            stateError=false
        }
        else
        {
            binding.data.append((view as Button).text)

        }
        lastNumeric=true
        OnEqualAcrion()

    }

    fun OnEqualAcrion(){
        if(lastNumeric && !stateError){
            val txt=binding.data.text.toString()
            expression=ExpressionBuilder(txt).build()
            try {
                val resultcal=expression.evaluate()

                binding.result.visibility=View.VISIBLE

                binding.result.text="="+ resultcal.toString()
            }
            catch (ex : ArithmeticException){
                Log.e("evalue error ", ex.toString())
                binding.result.text="Error"
                stateError=true
                lastNumeric=false
            }
        }





    }

}