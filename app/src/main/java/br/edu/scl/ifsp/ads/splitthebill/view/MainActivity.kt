package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.EXTRA_PARTICIPANT
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.VIEW_PARTICIPANT
import br.edu.scl.ifsp.ads.contatospdm.model.Participante
import br.edu.scl.ifsp.ads.contatospdm.view.ParticipantActivity
import br.edu.scl.ifsp.ads.splitthebill.adapter.ParticipanteAdapter

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    //Data source
    private val participantList: MutableList<Participante> = mutableListOf()
    //Adapter
    private val participantAdapter: ParticipanteAdapter by lazy{
        ParticipanteAdapter(
            this,
            participantList
        )
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolBarIn.toolbar)

        fillParticipantes()
        amb.participantLv.adapter = participantAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            //se o user clicar em aceite vou extrair um participante
            if (result.resultCode == RESULT_OK){
                                        // como se fosse um getParticipante
                val participante = result.data?.getParcelableExtra<Participante>(EXTRA_PARTICIPANT)
                participante?.let { _participant ->
                    //se o participante existe na linha substitui
                   if(participantList.any{ it.id == participante.id}) {
                       val position = participantList.indexOfFirst { it.id == _participant.id }
                       participantList[position] = _participant
                   } else{ //se nao, cria um novo id
                       participantList.add(_participant)
                   }
                    //comando que avisa quando um participante novo eh adicionado
                    participantAdapter.notifyDataSetChanged()
                }
            }
        }
        //findById
        amb.participantLv.setOnItemClickListener { adapterView, view, position, l ->
            val pa = participantList[position]
            val viewParticipanteIntent = Intent(this, ParticipantActivity::class.java)
            viewParticipanteIntent.putExtra(EXTRA_PARTICIPANT, pa)
            viewParticipanteIntent.putExtra(VIEW_PARTICIPANT, true)
            // carregando o usuário para outra tela
            startActivity(viewParticipanteIntent)
        }
        registerForContextMenu(amb.participantLv)
    }

    //vai tratar os dados do menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
//funcao que trata o evento de click no +
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.addParticipantMi -> {
                //Abrir a tela ParticipantActivity para adicionar
                //um novo participante.
                // uma intent dessa classe para a intent da participantActivity
                carl.launch(Intent(this, ParticipantActivity::class.java))
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId){
            R.id.removeParticipanteMi -> {
                participantList.removeAt(position)
                participantAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Participant removed.", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.updateParticipanteMi -> {
                val participante = participantList[position]
                val editParticipantIntent = Intent(this, ParticipantActivity::class.java)

                editParticipantIntent.putExtra(EXTRA_PARTICIPANT, participante)
                // carregando o participantw para outra tela
                carl.launch(editParticipantIntent)
                true
            }
            else -> { false }
        }

    }
    private fun fillParticipantes() {
            participantList.add(
                Participante(
                    1,
                    "Fernando",
                    "pagou 74,00",
                    "comprou ingredientes"
                )
            )
        participantList.add(
            Participante(
                2,
                "Lucas",
                "pagou 47,25",
                "comprou bebidas"
            )
        )
        participantList.add(
            Participante(
                3,
                "Silvana",
                "pagou 38,75",
                "comprou petiscos"
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.participantLv)
    }

}