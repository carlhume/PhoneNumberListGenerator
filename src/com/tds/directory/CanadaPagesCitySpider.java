package com.tds.directory;

import com.tds.repository.Repository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CanadaPagesCitySpider {

    private CityPageSpider cityPageSpider;
    private Repository repository;

    public CanadaPagesCitySpider() {
        cityPageSpider = new CityPageSpider();
        repository = new Repository();
    }

    public CanadaPagesCitySpider( CityPageSpider spider, Repository repositoryForStoringContacts ) {
        this.cityPageSpider = spider;
        this.repository = repositoryForStoringContacts;
    }

    public void storeNewContactsFor( String city ) throws IOException {
        String canadapagesUrlForCity = "https://www.canadapages.com/wp/on/" + city + "/a-z-names/";
        storeNewContactsForCityUrlByLetter( canadapagesUrlForCity );
    }

    public void storeNewContactsForCityUrlByLetter( String url ) throws IOException {
        for( char letter = 'A'; letter <= 'Z'; letter ++ ) {
            Collection<Contact> contacts = findContactsFor( url + letter + "/" );
            for( Contact contact : contacts ) {
                Contact possibleMatch = repository.getContactForPhoneNumber( contact.getTelephoneNumber() );
                if( contact.matches( possibleMatch ) ) {
                    // We have already recorded this contact
                    System.out.println( ">> cnh >> Contact found at " + contact.getFoundOnPage() + " was previously found at " + possibleMatch.getFoundOnPage() );
                } else {
                    repository.store(contact);
                }
            }
        }
    }

    public Collection<Contact> findContactsForCityUrlByLetter(String url) throws IOException {
        Collection<Contact> contacts = new ArrayList<>();

        for( char letter = 'A'; letter <= 'Z'; letter ++ ) {
            contacts.addAll( findContactsFor( url + letter + "/" ) );
        }
        return contacts;
    }

    public Collection<Contact> findContactsFor(String url) throws IOException {
        Collection<Contact> contacts = new ArrayList<>();

        int pagesToCrawl = findPageCountFor(url);
        // Canadapages doesn't support api calls with page numbers if there is only 1 page of data ...
        if( pagesToCrawl == 0 ) {
            contacts.addAll( this.cityPageSpider.findContactsFromCityPage( url ) );
        } else {
            for (int pageNumber = 1; pageNumber <= pagesToCrawl; pageNumber++) {
                contacts.addAll( this.cityPageSpider.findContactsFromCityPage( url + pageNumber + "/" ) );
            }
        }

        return contacts;
    }

    public int findPageCountFor(String url) throws IOException {
        int pageCount = 0;
        Document webpage = Jsoup.connect(url).get();
        Elements links = webpage.select("a[href]");
        for (Element link : links) {
            if (link.text().equals("[Last Page]")) {
                // The page count is the same as the last page number, which is the last segment of the api call in the link
                String lastPageUrl = link.attr("href");
                String[] lastPageUrlSegments = lastPageUrl.split("/");
                pageCount = Integer.valueOf(lastPageUrlSegments[lastPageUrlSegments.length - 1]);
                break;
            }
        }
        return pageCount;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(">> cnh >> Starting to Generate List");
        CanadaPagesCitySpider spider = new CanadaPagesCitySpider();
/*
        spider.storeNewContactsFor( "addison" );
        spider.storeNewContactsFor( "akwesasne" );;
        spider.storeNewContactsFor( "alexandria" );
        spider.storeNewContactsFor( "alfred" );
        spider.storeNewContactsFor( "almonte" );
        spider.storeNewContactsFor( "ameliasburg" );
        spider.storeNewContactsFor( "amherstview" );
        spider.storeNewContactsFor( "apple-hill" );
        spider.storeNewContactsFor( "arden" );
        spider.storeNewContactsFor( "ardoch" );
        spider.storeNewContactsFor( "ardoch" );
        spider.storeNewContactsFor( "arnprior" );
        spider.storeNewContactsFor( "ashton" );
        spider.storeNewContactsFor( "athens" );
        spider.storeNewContactsFor( "avonmore" );

        spider.storeNewContactsFor( "bainsville" );
        spider.storeNewContactsFor( "balderson" );
        spider.storeNewContactsFor( "bancroft" );
        spider.storeNewContactsFor( "barrys-bay" );
        spider.storeNewContactsFor( "batawa" );
        spider.storeNewContactsFor( "bath" );
        spider.storeNewContactsFor( "battersea" );
        spider.storeNewContactsFor( "beachburg" );
        spider.storeNewContactsFor( "belleville" );
        spider.storeNewContactsFor( "berwick" );
        spider.storeNewContactsFor( "bloomfield" );
        spider.storeNewContactsFor( "boulter" );
        spider.storeNewContactsFor( "bourget" );
        spider.storeNewContactsFor( "braeside" );
        spider.storeNewContactsFor( "brighton" );
        spider.storeNewContactsFor( "brinston" );
        spider.storeNewContactsFor( "brockville" );
        spider.storeNewContactsFor( "burnstown" );
        spider.storeNewContactsFor( "burritts-rapids" );

        spider.storeNewContactsFor( "calabogie" );
        spider.storeNewContactsFor( "camden-east" );
        spider.storeNewContactsFor( "cannifton" );
        spider.storeNewContactsFor( "cardiff" );
        spider.storeNewContactsFor( "cardinal" );
        spider.storeNewContactsFor( "carleton-place" );
        spider.storeNewContactsFor( "carlsbad-springs" );
        spider.storeNewContactsFor( "carp" );
        spider.storeNewContactsFor( "carrying-place" );
        spider.storeNewContactsFor( "casselman" );
        spider.storeNewContactsFor( "chalk-river" );
        spider.storeNewContactsFor( "cherry-valley" );
        spider.storeNewContactsFor( "chesterville" );
        spider.storeNewContactsFor( "chute-a-blondeau" );
        spider.storeNewContactsFor( "clarence-creek" );
        spider.storeNewContactsFor( "clarendon-station" );
        spider.storeNewContactsFor( "clayton" );
        spider.storeNewContactsFor( "cloyne" );
        spider.storeNewContactsFor( "cobden" );
        spider.storeNewContactsFor( "codrington" );
        spider.storeNewContactsFor( "coe-hill" );
        spider.storeNewContactsFor( "combermere" );
        spider.storeNewContactsFor( "consecon" );
        spider.storeNewContactsFor( "corbyville" );
        spider.storeNewContactsFor( "cornwall" );
        spider.storeNewContactsFor( "crysler" );
        spider.storeNewContactsFor( "cumberland" );
        spider.storeNewContactsFor( "curran" );

        spider.storeNewContactsFor( "dacre" );
        spider.storeNewContactsFor( "dalkeith" );
        spider.storeNewContactsFor( "deep-river" );
        spider.storeNewContactsFor( "delta" );
        spider.storeNewContactsFor( "denbigh" );
        spider.storeNewContactsFor( "deseronto" );
        spider.storeNewContactsFor( "douglas" );
        spider.storeNewContactsFor( "dunrobin" );
        spider.storeNewContactsFor( "dunvegan" );

        spider.storeNewContactsFor( "edwards" );
        spider.storeNewContactsFor( "eganville" );
        spider.storeNewContactsFor( "eldorado" );
        spider.storeNewContactsFor( "elgin" );
        spider.storeNewContactsFor( "elginburg" );
        spider.storeNewContactsFor( "elizabethtown" );
        spider.storeNewContactsFor( "embrun" );
        spider.storeNewContactsFor( "enterprise" );
        spider.storeNewContactsFor( "erinsville" );

        spider.storeNewContactsFor( "finch" );
        spider.storeNewContactsFor( "fitzroy-harbour" );
        spider.storeNewContactsFor( "flinton" );
        spider.storeNewContactsFor( "foresters-falls" );
        spider.storeNewContactsFor( "fournier" );
        spider.storeNewContactsFor( "foxboro" );
        spider.storeNewContactsFor( "foymount" );
        spider.storeNewContactsFor( "frankford" );
        spider.storeNewContactsFor( "frankville" );

        spider.storeNewContactsFor( "gananoque" );
        spider.storeNewContactsFor( "gilmour" );
        spider.storeNewContactsFor( "glen-robertson" );
        spider.storeNewContactsFor( "glenburnie" );
        spider.storeNewContactsFor( "gloucester" );
        spider.storeNewContactsFor( "godfrey" );
        spider.storeNewContactsFor( "golden-lake" );
        spider.storeNewContactsFor( "greely" );
        spider.storeNewContactsFor( "green-valley" );
        spider.storeNewContactsFor( "griffith" );

        spider.storeNewContactsFor( "haley-station" );
        spider.storeNewContactsFor( "hammond" );
        spider.storeNewContactsFor( "harrowsmith" );
        spider.storeNewContactsFor( "hartington" );
        spider.storeNewContactsFor( "hawkesbury" );
        spider.storeNewContactsFor( "hillier" );

        spider.storeNewContactsFor( "ingleside" );
        spider.storeNewContactsFor( "inkerman" );
        spider.storeNewContactsFor( "inverary" );
        spider.storeNewContactsFor( "iroquois" );

        spider.storeNewContactsFor( "jasper" );
        spider.storeNewContactsFor( "joyceville" );

        spider.storeNewContactsFor( "kaladar" );
        spider.storeNewContactsFor( "kanata" );
        spider.storeNewContactsFor( "kars" );
        spider.storeNewContactsFor( "kemptville" );
        spider.storeNewContactsFor( "kenmore" );
        spider.storeNewContactsFor( "killaloe" );
        spider.storeNewContactsFor( "kinburn" );
        spider.storeNewContactsFor( "kingston" );

        spider.storeNewContactsFor( "lanark" );
        spider.storeNewContactsFor( "lancaster" );
        spider.storeNewContactsFor( "lansdowne" );
        spider.storeNewContactsFor( "lefaivre" );
        spider.storeNewContactsFor( "limoges" );
        spider.storeNewContactsFor( "lombardy" );
        spider.storeNewContactsFor( "long-sault" );
        spider.storeNewContactsFor( "lunenburg" );
        spider.storeNewContactsFor( "lyn" );
        spider.storeNewContactsFor( "lyndhurst" );

        spider.storeNewContactsFor( "maberly" );
        spider.storeNewContactsFor( "mackey" );
        spider.storeNewContactsFor( "madawaska" );
        spider.storeNewContactsFor( "madoc" );
        spider.storeNewContactsFor( "maitland" );
        spider.storeNewContactsFor( "mallorytown" );
        spider.storeNewContactsFor( "manotick" );
        spider.storeNewContactsFor( "maple-leaf" );
        spider.storeNewContactsFor( "marlbank" );
        spider.storeNewContactsFor( "marmora" );
        spider.storeNewContactsFor( "martintown" );
        spider.storeNewContactsFor( "marysville" );
        spider.storeNewContactsFor( "maxville" );
        spider.storeNewContactsFor( "maynooth" );
        spider.storeNewContactsFor( "mcarthurs-mills" );
        spider.storeNewContactsFor( "mcdonalds-corners" );
        spider.storeNewContactsFor( "merrickville" );
        spider.storeNewContactsFor( "metcalfe" );
        spider.storeNewContactsFor( "milford" );
        spider.storeNewContactsFor( "monkland" );
        spider.storeNewContactsFor( "moose-creek" );
        spider.storeNewContactsFor( "morewood" );
        spider.storeNewContactsFor( "morrisburg" );
        spider.storeNewContactsFor( "mountain" );
        spider.storeNewContactsFor( "mountain-grove" );
        spider.storeNewContactsFor( "munster" );

        spider.storeNewContactsFor( "napanee" );
        spider.storeNewContactsFor( "navan" );
 */
        spider.storeNewContactsFor( "nepean" );
        spider.storeNewContactsFor( "newboro" );
        spider.storeNewContactsFor( "newburgh" );
        spider.storeNewContactsFor( "newington" );
        spider.storeNewContactsFor( "north-augusta" );
        spider.storeNewContactsFor( "north-gower" );
        spider.storeNewContactsFor( "north-lancaster" );
        spider.storeNewContactsFor( "northbrook" );

        spider.storeNewContactsFor( "odessa" );
        spider.storeNewContactsFor( "ompah" );
        spider.storeNewContactsFor( "orleans" );
        spider.storeNewContactsFor( "osgoode" );
        spider.storeNewContactsFor( "ottawa" );
        spider.storeNewContactsFor( "oxford-mills" );
        spider.storeNewContactsFor( "oxford-station" );

        spider.storeNewContactsFor( "pakenham" );
        spider.storeNewContactsFor( "palmer-rapids" );
        spider.storeNewContactsFor( "parham" );
        spider.storeNewContactsFor( "pembroke" );
        spider.storeNewContactsFor( "perth" );
        spider.storeNewContactsFor( "perth-road" );
        spider.storeNewContactsFor( "petawawa" );
        spider.storeNewContactsFor( "picton" );
        spider.storeNewContactsFor( "plainfield" );
        spider.storeNewContactsFor( "plantagenet" );
        spider.storeNewContactsFor( "plevna" );
        spider.storeNewContactsFor( "portland" );
        spider.storeNewContactsFor( "prescott" );

        spider.storeNewContactsFor( "quadeville" );
        spider.storeNewContactsFor( "quinte-west" );

        spider.storeNewContactsFor( "ramsayville" );
        spider.storeNewContactsFor( "renfrew" );
        spider.storeNewContactsFor( "richmond" );
        spider.storeNewContactsFor( "rideau-ferry" );
        spider.storeNewContactsFor( "roblin" );
        spider.storeNewContactsFor( "rockcliffe" );
        spider.storeNewContactsFor( "rockland" );
        spider.storeNewContactsFor( "rockport" );
        spider.storeNewContactsFor( "rolphton" );
        spider.storeNewContactsFor( "roslin" );
        spider.storeNewContactsFor( "round-lake-centre" );
        spider.storeNewContactsFor( "russell" );

        spider.storeNewContactsFor( "saint-pascal-baylon" );
        spider.storeNewContactsFor( "sarsfield" );
        spider.storeNewContactsFor( "seeleys-bay" );
        spider.storeNewContactsFor( "shannonville" );
        spider.storeNewContactsFor( "sharbot-lake" );
        spider.storeNewContactsFor( "smiths-falls" );
        spider.storeNewContactsFor( "snow-road-station" );
        spider.storeNewContactsFor( "south-lancaster" );
        spider.storeNewContactsFor( "south-mountain" );
        spider.storeNewContactsFor( "spencerville" );
        spider.storeNewContactsFor( "springbrook" );
        spider.storeNewContactsFor( "saint-albert" );
        spider.storeNewContactsFor( "saint-andrews-west" );
        spider.storeNewContactsFor( "saint-bernardin" );
        spider.storeNewContactsFor( "saint-eugene" );
        spider.storeNewContactsFor( "saint-isidore" );
        spider.storeNewContactsFor( "saint-anne-de-prescott" );
        spider.storeNewContactsFor( "stella" );
        spider.storeNewContactsFor( "stirling" );
        spider.storeNewContactsFor( "stittsville" );
        spider.storeNewContactsFor( "stonecliffe" );
        spider.storeNewContactsFor( "summerstown" );
        spider.storeNewContactsFor( "sydenham" );

        spider.storeNewContactsFor( "tamworth" );
        spider.storeNewContactsFor( "thomasburg" );
        spider.storeNewContactsFor( "tichborne" );
        spider.storeNewContactsFor( "toledo" );
        spider.storeNewContactsFor( "trenton" );
        spider.storeNewContactsFor( "tweed" );

        spider.storeNewContactsFor( "vanier" );
        spider.storeNewContactsFor( "vankleek-hill" );
        spider.storeNewContactsFor( "vars" );
        spider.storeNewContactsFor( "vernon" );
        spider.storeNewContactsFor( "verona" );

        spider.storeNewContactsFor( "wellington" );
        spider.storeNewContactsFor( "wendover" );
        spider.storeNewContactsFor( "westbrook" );
        spider.storeNewContactsFor( "westmeath" );
        spider.storeNewContactsFor( "westport" );
        spider.storeNewContactsFor( "white-lake" );
        spider.storeNewContactsFor( "whitney" );
        spider.storeNewContactsFor( "williamsburg" );
        spider.storeNewContactsFor( "williamstown" );
        spider.storeNewContactsFor( "wilno" );
        spider.storeNewContactsFor( "winchester" );
        spider.storeNewContactsFor( "winchester-springs" );
        spider.storeNewContactsFor( "wolfe-island" );
        spider.storeNewContactsFor( "woodlawn" );
        spider.storeNewContactsFor( "wooler" );

        spider.storeNewContactsFor( "yarker" );

        System.out.println( ">> cnh >> Finished generating list" );
        }

        }