# Hyppy

## Kentän valinta-näkymä
Yläpalkissa pelaaja voi valita vaikeusasteen tai siirtyä kentän tuonti -näkymään.

### Vaikeusasteet
* Easy (helppo) vaikeusasteella vihollisia eikä kolikoita ei generoida. Brick-palikat hajoavat hitaasti. 
* Medium (keskivaikea, oletusvaikeusaste) viholliset ja kolikot generoidaan tavalliseen tapaan. Brick-palikat hajoavat tavallisella nopeudella. 
* Hard (vaikea) kolikot generoidaan tavalliseen tapaan, mutta viholliset liikkuvat kaksinkertaisella nopeudella. Brick-palikat hajoavat nopeasti. 

Yläpalkin alapuolella on nähtävissä pelattavat kentät. Haluttua kenttää päästään pelaamaan valitsemalla halutun kentän kohdalla painike ”Play”. ”Play”-painikkeen vasemmalla puolella olevasta ”Highscores”-painikkeesta pelaaja pääsee tarkastelemaan kyseisen kentän piste-ennätyksiä. 

![Alt text](readme-images/1.png?raw=true "Aloitusnäkymä")


## Kentän tuonti -näkymä
Kentän tuonti -näkymästä pelaaja pystyy näkemään kaikki pelin eri palikat ja niiden RGB-arvot, mikä mahdollistaa sen, että pelaajan on mahdollista luoda omia kenttiä hyvinkin helposti.

![Alt text](readme-images/2.png?raw=true "Kentän tuonti -näkymä")

Pelissä kentät generoidaan .bmp-muodossa olevien kuvien avulla. Jokaiselle palikalle on asetettu oma RGB-väriarvo. Kuvassa yksi pikseli vastaa kentän yhtä palikkaa.  
Kentän luonti onnistuu lähes millä tahansa kuvanmuokkausohjelmalla. Halutun palikan väri syötetään kuvanmuokkausohjelman siveltimen ja aloitetaan piirtäminen. Jokainen kenttä tulee sisältää pelaajan ja maalin, muita rajoituksia kenttien luontiin ei ole. Lopuksi kenttä tallennetaan .bmp-muodossa haluttuun sijaintiin. 

### Omien kenttien tuominen
Pelaajan luomia kenttiä päästään pelaamaan kahdella eri tavalla:
1. Valitsemalla haluttu vaikeusaste kentän tuonti-valikon alareunasta ja valitsemalla ”Upload”-painike. Tämän jälkeen pelaaja valitsee haluamansa kentän ja viimeistelee tuonnin ”Open”-painiketta painamalla.
2. Pelaaja tallentaa luomansa kentän sijaintiin ”C:/temp/Hyppy/levels”. Kyseisessä sijainnissa olevat kentät tuodaan peliin automaattisesti seuraavan kerran, kun pelaaja siirtyy kentän valinta-näkymään. Tämän jälkeen pelaaja valitsee oman kenttänsä kenttäluettelosta aivan kuten oletuskenttiä.


![Alt text](readme-images/3.png?raw=true "Kentän luonti")

### Palikan tyypit ja niiden ominaisuudet
Pelissä palikat voivat olla joko taustapalikoita tai kiinteitä palikoita. Kiinteät palikat törmäävät pelaajan kanssa ja estävät pelaajaa liikkumasta eteenpäin. Taustapalikat ovat vain ulkonäöllisiä, eivätkä ne vaikuta pelaajan liikkumiseen.

#### BgAir	
* Pelin oletustaustapalikka.
#### BgCastle	
*	CastleBrick-palikan tyylinen taustapalikka
#### BgStone	
*	Luolissa käytettävä taustapalikka
#### Brick	
*	Palikka, joka hajoaa kosketuksesta tietyn viiveen jälkeen. Palikan hajoamisnopeuteen vaikuttaa pelaajan valitsema vaikeusaste.
#### CastleBrick	
*	Linnoissa käytettävä kiinteä palikka ilman erityisominaisuuksia.
#### Cloud
*	Käytetään taivaalla leijuville palikoille, ei erityisominaisuuksia.	
#### Coin	
*	Kerättävä kolikko. Pelaajan täytyy kerätä kaikki kolikot aktivoidakseen maalin.
*	Kolikoita ei generoida ”Easy”-vaikeusasteella.
#### Dirt	
*	Maassa käytetty kiinteä palikka, joka on ulkonäöltään mullan näköinen.
#### ElevatorHor	
*	Sivuttaissuunnassa liikkuva hissi-palikka. Palikka liikkuu hissin luonti kohdasta 5 palikkaa oikealle, minkä jälkeen hissin suunta vaihtuu käänteiseksi. Kun hissi saavuttaa luontisijaintinsa käännetään suunta uudelleen. Tämä sykli toistuu koko pelin ajan.
#### ElevatorVer	
*	Pystysuunnassa liikkuva hissi-palikka. Palikka liikkuu hissin luonti kohdasta 5 palikkaa alaspäin, minkä jälkeen hissin suunta vaihtuu käänteiseksi. Kun hissi saavuttaa luontisijaintinsa käännetään suunta uudelleen. Tämä sykli toistuu koko pelin ajan.
#### Enemy	
*	Palikka liikkuu luontisuunnasta 5 palikkaa oikealle, jonka jälkeen suunta vaihtuu käänteiseksi ja palikka jatkaa liikkumista vasemmalle, kunnes saavuttaa luontisijainnista 5 palikkaa vasemmalle olevan kohdan. Tämän jälkeen suunta vaihtuu uudelleen. Tämä sykli toistuu koko pelin ajan. Pelaajan koskettaessa Enemy-palikkaa peli päättyy.
#### Goal	
*	Pelin tavoitteena pelaajan on päästä mahdollisimman nopeasti kosketuksiin maali-palikkaan. Koskettaessa Maali-palikkaa peli päättyy ja nykyinen suoritusaika tallennetaan piste-ennätyksiin.
#### Grass	
*	Kiinteä palikka, jota käytetään maanpinnassa oleviin palikoihin, jos maan alla olevat palikat ovat dirt-palikoita. Tällä saadaan luotua alue, jossa on multaa sekä ruohoa.
#### Lava	
*	Tappava laava. Pelaajan koskettaessa laavaa peli päättyy.
#### LavaBrick	
*	Ominaisuuksiltaan sama kuin Lava. Palikan tyyli poikkeaa lava-palikasta siten, että palikan ympärillä on kehykset. Palikkaa käytetään tilanteissa, missä laava ei ole maanpinnalla vaan esimerkiksi ilmassa.
#### Pillar	
*	Kiinteä palikka, jolla ei ole erityisominaisuuksia vaan ainoastaan oma tyylinsä.
#### PillarTop	
*	Kiinteä palikka, jolla ei ole erityisominaisuuksia vaan ainoastaan oma tyylinsä. Käytetään pylväiden ylimpänä palikkana.
#### Player	
*	Pelaaja-palikka, joka määrittelee mihin sijaintiin pelaaja luodaan kartan luontivaiheessa.
#### Stone	
*	Kiinteä palikka, jolla ei ole erityisominaisuuksia vaan ainoastaan oma tyylinsä.
#### Torch	
*	Taustapalikka, jota käytetään luolissa luomaan luolamainen tunnelma. Ei erityisominaisuuksia.

## Kentän valinta-näkymä
Näkymän kautta pelaaja pääsee tarkastelemaan kyseisen kentän piste-ennätyksiä. Näkymän yläreunassa olevasta palkista pelaaja voi vaihtaa näytettävien piste-ennätyksien vaikeusastetta kyseisestä kentästä, koska jokaisen vaikeusasteen piste-ennätykset tallennetaan erikseen.
![Alt text](readme-images/4.png?raw=true "Piste-ennätykset")

## Pelinäkymä
Valittuaan halutun kentän siirrytään pelinäkymään. 
Pelissä käytetään seuraavia painikkeita:
*	W tai Välilyönti - Hyppy
*	A - Liiku vasemmalle
*	D - Liiku oikealle
*	R - Aloita pelattu taso alusta
* ESCAPE - Palaa takaisin kentän valinta-näkymään
Pelinäkymän yläreunassa näkyy tämän hetkinen suoritusaika ja kerättävien kolikoiden määrä, jos kentässä tarvitsee kerätä kolikoita. 

![Alt text](readme-images/5.png?raw=true "Pelinäkymä")

