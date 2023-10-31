package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import br.edu.scl.ifsp.ads.splitthebill.adapter.ParticipantAdapter
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Participante

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater) // tem que ser executado em algum momento para que meu obj seja executado
    }
    //Data source
    private val participantList: MutableList<Participante> = mutableListOf()
    //Adapter
    private val participantAdapter: ParticipantAdapter by lazy{
        ParticipantAdapter(
            this,
            participantList
        )
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.toolBarIn.toolbar)

    }
}