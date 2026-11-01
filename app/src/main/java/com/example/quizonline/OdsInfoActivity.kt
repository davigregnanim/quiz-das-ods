package com.example.quizonline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonline.databinding.ActivityOdsInfoBinding

class OdsInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOdsInfoBinding
    private lateinit var adapter: OdsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityOdsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.odsIntroText.text = "Em 25 de setembro de 2015, 193 líderes mundiais se comprometeram com 17 Metas Globais para alcançar 3 objetivos extraordinários nos próximos 15 anos: erradicar a pobreza extrema, combater a desigualdade/injustiça e conter as mudanças climáticas. Esses objetivos são conhecidos como os Objetivos de Desenvolvimento Sustentável (ODS). Saiba mais sobre cada uma das 17 metas globais abaixo:"

        val odsList = listOf(
            OdsModel("ODS 1", "Erradicação da pobreza", "O primeiro objetivo visa acabar com a pobreza em todas as suas formas e em todos os lugares até 2030. Isso envolve erradicar a pobreza extrema, reduzir a pobreza em todas as suas dimensões de acordo com as definições nacionais e garantir que todos os mais vulneráveis tenham acesso a proteção social, serviços básicos e recursos econômicos. "),
            OdsModel("ODS 2", "Fome zero e agricultura sustentável", "Visa garantir que todos tenham direitos iguais aos recursos econômicos, bem como o acesso a serviços básicos, propriedade e controle sobre a terra e outras formas de propriedade, herança, recursos naturais, novas tecnologias apropriadas e serviços financeiros."),
            OdsModel("ODS 3", "Saúde e Bem-Estar", "Apresenta metas como a redução drástica da mortalidade materna, o fim das mortes evitáveis de bebês e crianças, o combate a doenças transmissíveis como a AIDS, tuberculose, malária, entre outras. Também trata da redução das mortes prematuras por doenças não transmissíveis, como hipertensão e diabetes, além de álcool e tabaco também como alvo deste objetivo, e as mortes em situações de"),
            OdsModel("ODS 4", "Educação de qualidade", "Garante que todos os jovens concluam a educação básica, mas não somente isso como também todo o processo de educação formal deve primar pelo desenvolvimento das potencialidades dos indivíduos, desde a primeira infância até a formação técnica e superior."),
            OdsModel("ODS 5", "Igualdade de gênero", "Esse objetivo pede o fim da discriminação e da violência contra mulheres e meninas, além da dimensão da cidadania e do direito à vida, a meta trata também de questões econômicas, buscando reformas que levem a direitos iguais à propriedade, controle sobre a terra, recursos financeiros, herança e recursos naturais."),
            OdsModel("ODS 6", "Água Potável e Saneamento", "A preocupação com a existência de água potável e segura para todos é o centro desse ODS . Oferta de saneamento e higiene, uso racional pela indústria e agricultura aumentando a eficiência e marcos institucionais para favorecer a participação social, para controle do uso da água e monitoração da proteção do meio ambiente."),
            OdsModel("ODS 7", "Energia acessível e limpa", "Interliga diferentes níveis de poder: a energia é fundamental para a vida cotidiana, mas também para a produção industrial global. Com isso, os interessados no assunto são muitos e variados: vão desde as famílias que moram no campo, passando pelos municípios urbanos, chegando a Estados nacionais."),
            OdsModel("ODS 8", "Trabalho decente e crescimento econômico", "Trata-se da economia internacional, seja por metas de desempenho econômico ou por busca de eficiência e produtividade. Assim como o emprego decente, o empreendedorismo e o valor à criatividade e à inovação incentivam a formalização e o crescimento de micro, pequenas e médias empresas. Uma preocupação especial é com o trabalho para grupos sociais específicos, como as mulheres, pessoas com deficiência e os jovens. Além disso, a meta propõe o incentivo ao turismo sustentável, que gera empregos e promove a cultura, e o respeito aos direitos trabalhistas, inclusive de migrantes."),
            OdsModel("ODS 9", "Inovação e infraestrutura", "Vai desde a construção e manutenção de portos, aeroportos, ferrovias, terminais para escoamento da produção, até parques de inovação, passando por atendimento de redes de telecomunicações. O desenvolvimento tecnológico e a diversificação industrial têm papel central neste objetivo, uma vez que possibilita o ganho de eficiência na produção."),
            OdsModel("ODS 10", "Redução das desigualdades", "É necessário assegurar renda às populações mais pobres, promover a inclusão social e política, e adotar políticas de proteção salarial. Medidas institucionais, como a eliminação de leis discriminatórias e a promoção de leis adequadas fazem parte do caminho para a redução das desigualdades. Por fim, fiscalizar e regular os mercados financeiros de forma a não concentrarem renda, e dar tratamento justo a migrantes."),
            OdsModel("ODS 11", "Cidades e comunidades sustentáveis", "Cidades mais inclusivas, seguras, sustentáveis e resilientes a desastres ou a eventos incomuns são as metas deste objetivo. Alguns pontos são: a urbanização de favelas, melhoria da oferta de serviços de transporte, atendimento para todos os tipos de grupos, preservação da cultura, redução do número de mortes de pessoas afetadas por catástrofes e de prejuízos, e a redução dos impactos ambientais e produção econômica nas cidades."),
            OdsModel("ODS 12", "Consumo e produção responsáveis", "Tem o foco em ações globais e locais, como alcançar o uso eficiente de recursos naturais, reduzir o desperdício de alimentos e manejar resíduos químicos de maneira responsável. Também estão incluídos o cuidado com resíduos sólido e diminuição da emissão de poluentes."),
            OdsModel("ODS 13", "Ação contra a Mudança Global do Clima", "Este objetivo confere importância à resiliência e a capacidade de adaptação dos agrupamentos humanos frente aos riscos associados ao clima e às catástrofes naturais. No entanto, o foco central é combater a mudança climática e seus impactos."),
            OdsModel("ODS 14", "Vida de baixo d`água", "Uma preocupação importante é com a redução da poluição, enfrentando, por exemplo, a acidificação dos mares. A pesca deve ser regulada, a fim de restaurar populações de peixes, para que haja um rendimento máximo e sustentável. Neste sentido, devem ser preservados também o acesso ao mar, aos recursos naturais e aos mercados ao pescador artesanal."),
            OdsModel("ODS 15", "Vida terrestre", "A preocupação não se dá só com a preservação e/ou conservação do que já existe, mas também com a reversão de danos já causados ao ambiente: deter o desmatamento, processos de desertificação que devem ser combatidos, preservação da biodiversidade e de espécies ameaçadas, acabar com a caça ilegal e com o tráfico de espécies da fauna e da flora."),
            OdsModel("ODS 16", "Paz, justiça e instituições fortes", "Neste objetivo entram metas associadas à redução de mortes por violência, tráfico e tortura contra crianças, e à promoção de um Estado de Direito em que todos tenham direito a se defender. Crimes internacionais, como o tráfico de armas também são abordados. A participação social nas instituições de poder, amparada por acesso público à informação, traz a dimensão da representação e da governança neste ODS."),
            OdsModel("ODS 17", "Parcerias em prol das metas", "Trata-se de um objetivo mais voltado para a ação internacional de auxílio a países em desenvolvimento, ampliando as possibilidades de investimento, transferência tecnológica, comércio multilateral. Mas também dá conta dos meios de implementação, tendo objetivos para finanças, tecnologia, capacitação, comércio, coerência de políticas e de instituições, parcerias multissetoriais, entre outros."),
        )

        adapter = OdsAdapter(odsList)
        binding.recyclerOds.layoutManager = LinearLayoutManager(this)
        binding.recyclerOds.adapter = adapter
    }
}
