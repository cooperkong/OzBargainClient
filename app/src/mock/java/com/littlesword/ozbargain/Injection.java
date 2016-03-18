package com.littlesword.ozbargain;

import com.littlesword.ozbargain.mocknetwork.MockAPIImp;
import com.littlesword.ozbargain.network.APIInterface;

/**
 * Created by kongw1 on 13/03/16.
 */
public class Injection {

    private Injection(){
        //no instance
    }

    public static APIInterface getAPIImp(){
        return new MockAPIImp();
    }
}
