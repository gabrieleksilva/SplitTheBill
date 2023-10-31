package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityParticipantBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.EXTRA_PARTICIPANT
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.SIZE
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.VIEW_PARTICIPANT
import br.edu.scl.ifsp.ads.contatospdm.model.Participante
import kotlin.random.Random

class CalcularActivity : AppCompatActivity() {
    private val acb: ActivityParticipantBinding by lazy {
        ActivityParticipantBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        setSupportActionBar(acb.toolBarIn.toolbar)
        supportActionBar?.subtitle = "Contact details"


        //recebendo o contato na hora que clica em editar
        val receivedContact = intent.getParcelableExtra<Participante>(EXTRA_PARTICIPANT)
        val size = intent.getParcelableExtra<Participante>(SIZE)

        //se não for nullo..
        receivedContact?.let { _receivedContact ->
            val viewContact = intent.getBooleanExtra(VIEW_PARTICIPANT, false)
            with(acb) {
                //find By id
                if (viewContact) {
                    //val totalGasto: Int?;
                    //totalGasto = valorEt.toString().toInt() - 160

                    //Desativando a edição dos campos de texto nameEt, addressEt, phoneEt e emailEt (tornando-os apenas leitura)
                    nameEt.isEnabled = false
                    valorEt.isEnabled = false
                    descEt.isEnabled = false
                    //Tornando o botão saveBt invisível (removendo-o da tela).
                    saveBt.visibility = View.GONE
                }
                nameEt.setText(_receivedContact.name)
                valorEt.setText(_receivedContact.qtde_paga.toString())
                descEt.setText(_receivedContact.descricao)
            }
        }
    }

}




