package com.tds.directory;

import com.tds.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneNumberListGenerator {

    private final Repository repository;
    private final CityPageSpider spider;

    public PhoneNumberListGenerator( CityPageSpider spiderForCrawling, Repository repositoryForStoringContactData ) {
        this.repository = repositoryForStoringContactData;
        this.spider = spiderForCrawling;
    }

    public void storeNewContactsFor( Collection<CrawlData> citiesToCrawl ) throws IOException {
        for( CrawlData cityToCrawl : citiesToCrawl ) {
            System.out.println( ">> cnh >> Starting to crawl " + cityToCrawl.getCity() );
            storeNewContactsFor( cityToCrawl.getCity(), cityToCrawl.getPageCount() );
        }
    }

    public void storeNewContactsFor( String city, int numberOfPages ) throws IOException {

        for( int pageCounter = 1; pageCounter <= numberOfPages; pageCounter++ ) {
            Collection<Contact> contacts = spider.findContactsFromCityPage( "https://411.ca/white-pages/on/" + city + "/p" + pageCounter );

            for (Contact contact : contacts) {
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


    public static void main( String [] args ) throws IOException {
        System.out.println( ">> cnh >> Starting to Generate List" );

        Collection<CrawlData> citiesToCrwal = new ArrayList<>();

        citiesToCrwal.add( new CrawlData( "addison", 1 ) );
        citiesToCrwal.add( new CrawlData( "akwesasne", 1 ) );
        citiesToCrwal.add( new CrawlData( "alexandria", 8 ) );
        citiesToCrwal.add( new CrawlData( "alfred", 4 ) );
        citiesToCrwal.add( new CrawlData( "almonte", 17 ) );
        citiesToCrwal.add( new CrawlData( "ameliasburg", 1 ) );
        citiesToCrwal.add( new CrawlData( "amherstview", 12 ) );
        citiesToCrwal.add( new CrawlData( "apple-hill", 2 ) );
        citiesToCrwal.add( new CrawlData( "arden", 3 ) );
        citiesToCrwal.add( new CrawlData( "ardoch", 1 ) );
        citiesToCrwal.add( new CrawlData( "ardoch", 1 ) );
        citiesToCrwal.add( new CrawlData( "arnprior", 19 ) );
        citiesToCrwal.add( new CrawlData( "ashton", 4 ) );
        citiesToCrwal.add( new CrawlData( "athens", 6 ) );
        citiesToCrwal.add( new CrawlData( "avonmore", 2 ) );

        citiesToCrwal.add( new CrawlData( "bainsville", 2 ) );
        citiesToCrwal.add( new CrawlData( "balderson", 1 ) );
        citiesToCrwal.add( new CrawlData( "bancroft", 12 ) );
        citiesToCrwal.add( new CrawlData( "barrys-bay", 9 ) );
        citiesToCrwal.add( new CrawlData( "batawa", 1 ) );
        citiesToCrwal.add( new CrawlData( "bath", 7 ) );
        citiesToCrwal.add( new CrawlData( "battersea", 1 ) );
        citiesToCrwal.add( new CrawlData( "beachburg", 5 ) );
        citiesToCrwal.add( new CrawlData( "belleville", 52 ) );
        citiesToCrwal.add( new CrawlData( "berwick", 1 ) );
        citiesToCrwal.add( new CrawlData( "bloomfield", 4 ) );
        citiesToCrwal.add( new CrawlData( "boulter", 1 ) );
        citiesToCrwal.add( new CrawlData( "bourget", 3 ) );
        citiesToCrwal.add( new CrawlData( "braeside", 3 ) );
        citiesToCrwal.add( new CrawlData( "brighton", 18 ) );
        citiesToCrwal.add( new CrawlData( "brinston", 1 ) );
        citiesToCrwal.add( new CrawlData( "brockville", 32 ) );
        citiesToCrwal.add( new CrawlData( "burnstown", 1 ) );
        citiesToCrwal.add( new CrawlData( "burritts-rapids", 1 ) );

        citiesToCrwal.add( new CrawlData( "calabogie", 4 ) );
        citiesToCrwal.add( new CrawlData( "camden-east", 1 ) );
        citiesToCrwal.add( new CrawlData( "cannifton", 1 ) );
        citiesToCrwal.add( new CrawlData( "cardiff", 4 ) );
        citiesToCrwal.add( new CrawlData( "cardinal", 4 ) );
        citiesToCrwal.add( new CrawlData( "carleton-place", 18 ) );
        citiesToCrwal.add( new CrawlData( "carlsbad-springs", 3 ) );
        citiesToCrwal.add( new CrawlData( "carp", 13 ) );
        citiesToCrwal.add( new CrawlData( "carrying-place", 4 ) );
        citiesToCrwal.add( new CrawlData( "casselman", 5 ) );
        citiesToCrwal.add( new CrawlData( "chalk-river", 3 ) );
        citiesToCrwal.add( new CrawlData( "cherry-valley", 1 ) );
        citiesToCrwal.add( new CrawlData( "chesterville", 5 ) );
        citiesToCrwal.add( new CrawlData( "chute-a-blondeau", 1 ) );
        citiesToCrwal.add( new CrawlData( "clarence-creek", 4 ) );
        citiesToCrwal.add( new CrawlData( "clarendon-station", 1 ) );
        citiesToCrwal.add( new CrawlData( "clayton", 2 ) );
        citiesToCrwal.add( new CrawlData( "cloyne", 2 ) );
        citiesToCrwal.add( new CrawlData( "cobden", 6 ) );
        citiesToCrwal.add( new CrawlData( "codrington", 1 ) );
        citiesToCrwal.add( new CrawlData( "coe-hill", 2 ) );
        citiesToCrwal.add( new CrawlData( "combermere", 2 ) );
        citiesToCrwal.add( new CrawlData( "consecon", 2 ) );
        citiesToCrwal.add( new CrawlData( "corbyville", 2 ) );
        citiesToCrwal.add( new CrawlData( "cormac", 1 ) );
        citiesToCrwal.add( new CrawlData( "cornwall", 42 ) );
        citiesToCrwal.add( new CrawlData( "crysler", 3 ) );
        citiesToCrwal.add( new CrawlData( "cumberland", 9 ) );
        citiesToCrwal.add( new CrawlData( "curran", 2 ) );

        citiesToCrwal.add( new CrawlData( "dacre", 1 ) );
        citiesToCrwal.add( new CrawlData( "dalkeith", 1 ) );
        citiesToCrwal.add( new CrawlData( "deep-river", 9 ) );
        citiesToCrwal.add( new CrawlData( "delta", 3 ) );
        citiesToCrwal.add( new CrawlData( "denbigh", 3 ) );
        citiesToCrwal.add( new CrawlData( "deseronto", 5 ) );
        citiesToCrwal.add( new CrawlData( "douglas", 2 ) );
        citiesToCrwal.add( new CrawlData( "dunrobin", 6 ) );
        citiesToCrwal.add( new CrawlData( "dunvegan", 1 ) );

        citiesToCrwal.add( new CrawlData( "edwards", 1 ) );
        citiesToCrwal.add( new CrawlData( "eganville", 6 ) );
        citiesToCrwal.add( new CrawlData( "eldorado", 1 ) );
        citiesToCrwal.add( new CrawlData( "elgin", 6 ) );
        citiesToCrwal.add( new CrawlData( "elginburg", 2 ) );
        citiesToCrwal.add( new CrawlData( "elizabethtown", 1 ) );
        citiesToCrwal.add( new CrawlData( "embrun", 11 ) );
        citiesToCrwal.add( new CrawlData( "enterprise", 2 ) );
        citiesToCrwal.add( new CrawlData( "erinsville", 1 ) );

        citiesToCrwal.add( new CrawlData( "finch", 2 ) );
        citiesToCrwal.add( new CrawlData( "fitzroy-harbour", 2 ) );
        citiesToCrwal.add( new CrawlData( "flinton", 1 ) );
        citiesToCrwal.add( new CrawlData( "foresters-falls", 1 ) );
        citiesToCrwal.add( new CrawlData( "fournier", 1 ) );
        citiesToCrwal.add( new CrawlData( "foxboro", 3 ) );
        citiesToCrwal.add( new CrawlData( "foymount", 2 ) );
        citiesToCrwal.add( new CrawlData( "frankford", 8 ) );
        citiesToCrwal.add( new CrawlData( "frankville", 1 ) );

        citiesToCrwal.add( new CrawlData( "gananoque", 12 ) );
        citiesToCrwal.add( new CrawlData( "gilmour", 4 ) );
        citiesToCrwal.add( new CrawlData( "glen-robertson", 3 ) );
        citiesToCrwal.add( new CrawlData( "glenburnie", 4 ) );
        citiesToCrwal.add( new CrawlData( "gloucester", 68 ) );
        citiesToCrwal.add( new CrawlData( "godfrey", 2 ) );
        citiesToCrwal.add( new CrawlData( "golden-lake", 3 ) );
        citiesToCrwal.add( new CrawlData( "greely", 14 ) );
        citiesToCrwal.add( new CrawlData( "green-valley", 2 ) );
        citiesToCrwal.add( new CrawlData( "griffith", 1 ) );

        citiesToCrwal.add( new CrawlData( "haley-station", 1 ) );
        citiesToCrwal.add( new CrawlData( "hammond", 3 ) );
        citiesToCrwal.add( new CrawlData( "harrowsmith", 5 ) );
        citiesToCrwal.add( new CrawlData( "hartington", 1 ) );
        citiesToCrwal.add( new CrawlData( "hawkesbury", 11 ) );
        citiesToCrwal.add( new CrawlData( "hillier", 1 ) );

        citiesToCrwal.add( new CrawlData( "ingleside", 6 ) );
        citiesToCrwal.add( new CrawlData( "inkerman", 1 ) );
        citiesToCrwal.add( new CrawlData( "inverary", 10 ) );
        citiesToCrwal.add( new CrawlData( "iroquois", 5 ) );

        citiesToCrwal.add( new CrawlData( "jasper", 2 ) );
        citiesToCrwal.add( new CrawlData( "joyceville", 1 ) );

        citiesToCrwal.add( new CrawlData( "kaladar", 1 ) );
        citiesToCrwal.add( new CrawlData( "kanata", 87 ) );
        citiesToCrwal.add( new CrawlData( "kars", 3 ) );
        citiesToCrwal.add( new CrawlData( "kemptville", 19 ) );
        citiesToCrwal.add( new CrawlData( "kenmore", 1 ) );
        citiesToCrwal.add( new CrawlData( "killaloe", 7 ) );
        citiesToCrwal.add( new CrawlData( "kinburn", 5 ) );
        citiesToCrwal.add( new CrawlData( "kingston", 105 ) );

        citiesToCrwal.add( new CrawlData( "lanark", 6 ) );
        citiesToCrwal.add( new CrawlData( "lancaster", 6 ) );
        citiesToCrwal.add( new CrawlData( "lansdowne", 9 ) );
        citiesToCrwal.add( new CrawlData( "lefaivre", 1 ) );
        citiesToCrwal.add( new CrawlData( "limoges", 4 ) );
        citiesToCrwal.add( new CrawlData( "lombardy", 3 ) );
        citiesToCrwal.add( new CrawlData( "long-sault", 7 ) );
        citiesToCrwal.add( new CrawlData( "lunenburg", 2 ) );
        citiesToCrwal.add( new CrawlData( "lyn", 2 ) );
        citiesToCrwal.add( new CrawlData( "lyndhurst", 2 ) );

        citiesToCrwal.add( new CrawlData( "maberly", 3 ) );
        citiesToCrwal.add( new CrawlData( "mackey", 1 ) );
        citiesToCrwal.add( new CrawlData( "madawaska", 1 ) );
        citiesToCrwal.add( new CrawlData( "madoc", 8 ) );
        citiesToCrwal.add( new CrawlData( "maitland", 3 ) );
        citiesToCrwal.add( new CrawlData( "mallorytown", 5 ) );
        citiesToCrwal.add( new CrawlData( "manotick", 22 ) );
        citiesToCrwal.add( new CrawlData( "maple-leaf", 1 ) );
        citiesToCrwal.add( new CrawlData( "marlbank", 1 ) );
        citiesToCrwal.add( new CrawlData( "marmora", 8 ) );
        citiesToCrwal.add( new CrawlData( "martintown", 3 ) );
        citiesToCrwal.add( new CrawlData( "marysville", 2 ) );
        citiesToCrwal.add( new CrawlData( "maxville", 4 ) );
        citiesToCrwal.add( new CrawlData( "maynooth", 4 ) );
        citiesToCrwal.add( new CrawlData( "mcarthurs-mills", 1 ) );
        citiesToCrwal.add( new CrawlData( "mcdonalds-corners", 2 ) );
        citiesToCrwal.add( new CrawlData( "merrickville", 5 ) );
        citiesToCrwal.add( new CrawlData( "metcalfe", 8 ) );
        citiesToCrwal.add( new CrawlData( "milford", 1 ) );
        citiesToCrwal.add( new CrawlData( "monkland", 1 ) );
        citiesToCrwal.add( new CrawlData( "moose-creek", 3 ) );
        citiesToCrwal.add( new CrawlData( "morewood", 1 ) );
        citiesToCrwal.add( new CrawlData( "morrisburg", 6 ) );
        citiesToCrwal.add( new CrawlData( "mountain", 2 ) );
        citiesToCrwal.add( new CrawlData( "mountain-grove", 1 ) );
        citiesToCrwal.add( new CrawlData( "munster", 3 ) );

        citiesToCrwal.add( new CrawlData( "napanee", 16 ) );
        citiesToCrwal.add( new CrawlData( "navan", 7 ) );
        citiesToCrwal.add( new CrawlData( "nepean", 126 ) );
        citiesToCrwal.add( new CrawlData( "newboro", 1 ) );
        citiesToCrwal.add( new CrawlData( "newburgh", 3 ) );
        citiesToCrwal.add( new CrawlData( "newington", 1 ) );
        citiesToCrwal.add( new CrawlData( "north-augusta", 3 ) );
        citiesToCrwal.add( new CrawlData( "north-gower", 6 ) );
        citiesToCrwal.add( new CrawlData( "north-lancaster", 1 ) );
        citiesToCrwal.add( new CrawlData( "northbrook", 9 ) );

        citiesToCrwal.add( new CrawlData( "odessa", 5 ) );
        citiesToCrwal.add( new CrawlData( "ompah", 1 ) );
        citiesToCrwal.add( new CrawlData( "orleans", 101 ) );
        citiesToCrwal.add( new CrawlData( "osgoode", 7 ) );
        citiesToCrwal.add( new CrawlData( "ottawa", 295 ) );
        citiesToCrwal.add( new CrawlData( "oxford-mills", 2 ) );
        citiesToCrwal.add( new CrawlData( "oxford-station", 1 ) );

        citiesToCrwal.add( new CrawlData( "pakenham", 3 ) );
        citiesToCrwal.add( new CrawlData( "palmer-rapids", 3 ) );
        citiesToCrwal.add( new CrawlData( "parham", 5 ) );
        citiesToCrwal.add( new CrawlData( "pembroke", 28 ) );
        citiesToCrwal.add( new CrawlData( "perth", 23 ) );
        citiesToCrwal.add( new CrawlData( "perth-road", 1 ) );
        citiesToCrwal.add( new CrawlData( "petawawa", 15 ) );
        citiesToCrwal.add( new CrawlData( "picton", 16 ) );
        citiesToCrwal.add( new CrawlData( "plainfield", 2 ) );
        citiesToCrwal.add( new CrawlData( "plantagenet", 4 ) );
        citiesToCrwal.add( new CrawlData( "plevna", 4 ) );
        citiesToCrwal.add( new CrawlData( "portland", 5 ) );
        citiesToCrwal.add( new CrawlData( "prescott", 11 ) );

        citiesToCrwal.add( new CrawlData( "quadeville", 1 ) );
        citiesToCrwal.add( new CrawlData( "quinte-west", 1 ) );

        citiesToCrwal.add( new CrawlData( "ramsayville", 1 ) );
        citiesToCrwal.add( new CrawlData( "renfrew", 18 ) );
        citiesToCrwal.add( new CrawlData( "richmond", 11 ) );
        citiesToCrwal.add( new CrawlData( "rideau-ferry", 1 ) );
        citiesToCrwal.add( new CrawlData( "roblin", 1 ) );
        citiesToCrwal.add( new CrawlData( "rockcliffe", 4 ) );
        citiesToCrwal.add( new CrawlData( "rockland", 13 ) );
        citiesToCrwal.add( new CrawlData( "rockport", 1 ) );
        citiesToCrwal.add( new CrawlData( "rolphton", 1 ) );
        citiesToCrwal.add( new CrawlData( "roslin", 2 ) );
        citiesToCrwal.add( new CrawlData( "round-lake-centre", 1 ) );
        citiesToCrwal.add( new CrawlData( "russell", 12 ) );

        citiesToCrwal.add( new CrawlData( "saint-pascal-baylon", 1 ) );
        citiesToCrwal.add( new CrawlData( "sarsfield", 1 ) );
        citiesToCrwal.add( new CrawlData( "seeleys-bay", 5 ) );
        citiesToCrwal.add( new CrawlData( "shannonville", 1 ) );
        citiesToCrwal.add( new CrawlData( "sharbot-lake", 7 ) );
        citiesToCrwal.add( new CrawlData( "smiths-falls", 22 ) );
        citiesToCrwal.add( new CrawlData( "snow-road-station", 1 ) );
        citiesToCrwal.add( new CrawlData( "south-lancaster", 1 ) );
        citiesToCrwal.add( new CrawlData( "south-mountain", 4 ) );
        citiesToCrwal.add( new CrawlData( "spencerville", 4 ) );
        citiesToCrwal.add( new CrawlData( "springbrook", 1 ) );
        citiesToCrwal.add( new CrawlData( "saint-albert", 2 ) );
        citiesToCrwal.add( new CrawlData( "saint-andrews-west", 2 ) );
        citiesToCrwal.add( new CrawlData( "saint-bernardin", 1 ) );
        citiesToCrwal.add( new CrawlData( "saint-eugene", 2 ) );
        citiesToCrwal.add( new CrawlData( "saint-isidore", 2 ) );
        citiesToCrwal.add( new CrawlData( "saint-anne-de-prescott", 1 ) );
        citiesToCrwal.add( new CrawlData( "stella", 1 ) );
        citiesToCrwal.add( new CrawlData( "stirling", 9 ) );
        citiesToCrwal.add( new CrawlData( "stittsville", 39 ) );
        citiesToCrwal.add( new CrawlData( "stonecliffe", 1 ) );
        citiesToCrwal.add( new CrawlData( "summerstown", 2 ) );
        citiesToCrwal.add( new CrawlData( "sydenham", 6 ) );

        citiesToCrwal.add( new CrawlData( "tamworth", 3 ) );
        citiesToCrwal.add( new CrawlData( "thomasburg", 1 ) );
        citiesToCrwal.add( new CrawlData( "tichborne", 1 ) );
        citiesToCrwal.add( new CrawlData( "toledo", 3 ) );
        citiesToCrwal.add( new CrawlData( "trenton", 30 ) );
        citiesToCrwal.add( new CrawlData( "tweed", 7 ) );

        citiesToCrwal.add( new CrawlData( "vanier", 17 ) );
        citiesToCrwal.add( new CrawlData( "vankleek-hill", 5 ) );
        citiesToCrwal.add( new CrawlData( "vars", 3 ) );
        citiesToCrwal.add( new CrawlData( "vernon", 1 ) );
        citiesToCrwal.add( new CrawlData( "verona", 5 ) );

        citiesToCrwal.add( new CrawlData( "wellington", 5 ) );
        citiesToCrwal.add( new CrawlData( "wendover", 2 ) );
        citiesToCrwal.add( new CrawlData( "westbrook", 1 ) );
        citiesToCrwal.add( new CrawlData( "westmeath", 4 ) );
        citiesToCrwal.add( new CrawlData( "westport", 10 ) );
        citiesToCrwal.add( new CrawlData( "white-lake", 2 ) );
        citiesToCrwal.add( new CrawlData( "whitney", 3 ) );
        citiesToCrwal.add( new CrawlData( "williamsburg", 3 ) );
        citiesToCrwal.add( new CrawlData( "williamstown", 4 ) );
        citiesToCrwal.add( new CrawlData( "wilno", 1 ) );
        citiesToCrwal.add( new CrawlData( "winchester", 7 ) );
        citiesToCrwal.add( new CrawlData( "winchester-springs", 1 ) );
        citiesToCrwal.add( new CrawlData( "wolfe-island", 3 ) );
        citiesToCrwal.add( new CrawlData( "woodlawn", 7 ) );
        citiesToCrwal.add( new CrawlData( "wooler", 2 ) );

        citiesToCrwal.add( new CrawlData( "yarker", 3 ) );

        PhoneNumberListGenerator generator = new PhoneNumberListGenerator( new CityPageSpider(), new Repository() );
        generator.storeNewContactsFor( citiesToCrwal );
        System.out.println( ">> cnh >> Finished generating list" );
    }

}
