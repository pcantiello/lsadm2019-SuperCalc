package it.unicampania.lsadm.supercalc

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*


/**
 * SuperCalc: Activity principale
 * Corso LSADM anno 2018/19
 * Pasquale Cantiello
 */
class MainActivity : AppCompatActivity() {

    // Enumerazione con attributo
    enum class Operazione(val op:String) { SOMMA("+"), SOTTRAZIONE("-"), MOLTIPLICAZIONE("*"), DIVISIONE("/") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Impostazione dei listner
        btnSomma.setOnClickListener { calcola(Operazione.SOMMA) }
        btnSottrai.setOnClickListener { calcola(Operazione.SOTTRAZIONE) }
        btnMoltiplica.setOnClickListener { calcola(Operazione.MOLTIPLICAZIONE) }
        btnDividi.setOnClickListener { calcola(Operazione.DIVISIONE) }

    }

    /**
     * Effettua il calcolo
     */
    private fun calcola(operazione: Operazione) {

        nascondiTastiera()

        // Estrai i due operandi
        val op1 = editPrimo.text.toString()
        val op2 = editSecondo.text.toString()

        try {
            // La conversione può generare un'eccezione
            val a = op1.toDouble()
            val b = op2.toDouble()

            var risultato: Double

            // Effettua il calcolo
            when(operazione) {
                Operazione.SOMMA -> risultato = a + b
                Operazione.SOTTRAZIONE -> risultato = a - b
                Operazione.MOLTIPLICAZIONE -> risultato = a * b
                Operazione.DIVISIONE ->  {
                    if (b != 0.0)
                        risultato = a / b
                    else {
                        textRisultato.text = getString(R.string.divbyzero)
                        return
                    }
                }
            }
            // Visualizza il risultato
            textRisultato.text = String.format(getString(R.string.risultato), a, operazione.op, b, risultato)
        }
        catch (e: NumberFormatException) {
            // Intercetta possibili eccezioni, ad esempio quando uno degli operandi non rappresenti un numero
            textRisultato.text =  getString(R.string.opInvalidi)
        }
    }

    /**
     * Nasconde la tastiera
     */
    private fun nascondiTastiera() {
        val view = this.getCurrentFocus()
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
