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
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityListaBinding
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.EXTRA_PARTICIPANT
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.SIZE
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.VIEW_PARTICIPANT
import br.edu.scl.ifsp.ads.contatospdm.model.Participante
import br.edu.scl.ifsp.ads.splitthebill.adapter.ParticipanteAdapter

class ListaActivity : AppCompatActivity() {
    private val amb: ActivityListaBinding by lazy {
        ActivityListaBinding.inflate(layoutInflater)
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
        amb.listaLv.adapter = participantAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            //se o user clicar em aceite vou extrair um participante
            if (result.resultCode == RESULT_OK){
                val pa = participantList
                val size = participantList.size
                // como se fosse um getParticipante
                val participante = result.data?.getParcelableExtra<Participante>(EXTRA_PARTICIPANT)
                participante?.let { _participant ->

                    //se o participante existe na linha substitui
                    if(participantList.any{ it.id == participante.id}) {
                        val position = participantList.indexOfFirst { it.id == _participant.id}
                        participantList[position] = _participant

                        val qutde_anterior = _participant.qtde_paga
                        _participant.qtde_paga = qutde_anterior - (160/size)

                    } else{ //se nao, cria um novo id
                        participantList.add(_participant)
                    }
                    //comando que avisa quando um participante novo eh adicionado
                    participantAdapter.notifyDataSetChanged()
                }
            }
        }
        //findById
        amb.listaLv.setOnItemClickListener { adapterView, view, position, l ->
            val pa = participantList[position]
            val size = participantList.size
            val qutde_anterior = pa.qtde_paga
            pa.qtde_paga = qutde_anterior - (160/size)
            val viewParticipanteIntent = Intent(this, CalcularActivity::class.java)
            viewParticipanteIntent.putExtra(EXTRA_PARTICIPANT, pa)
            viewParticipanteIntent.putExtra(VIEW_PARTICIPANT, true)
            // carregando o usuário para outra tela
            startActivity(viewParticipanteIntent)
        }
        registerForContextMenu(amb.listaLv)
    }

    //vai tratar os dados do menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }
    private fun fillParticipantes() {
            participantList.add(
                Participante(
                    1,
                    "Fernando",
                    74.00,
                    "comprou ingredientes"
                )
            )
            participantList.add(
                Participante(
                    2,
                    "Lucas",
                    47.25,
                    "comprou bebidas"
                )
            )
            participantList.add(
                Participante(
                    3,
                    "Silvana",
                    38.75,
                    "comprou petiscos"
                )
            )
        participantList.add(
            Participante(
                4,
                "Eloize",
                0.0,
                "nada"
            )
        )
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.listaLv)
    }

}