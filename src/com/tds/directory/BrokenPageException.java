package com.tds.directory;

import java.io.IOException;

public class BrokenPageException extends IOException {

    public BrokenPageException( String page ) {
        super( page + " is broken" );
    }


}
