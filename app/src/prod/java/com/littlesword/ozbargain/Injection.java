package com.littlesword.ozbargain;

import com.littlesword.ozbargain.network.APIImp;
import com.littlesword.ozbargain.network.APIInterface;

/**
 * Created by kongw1 on 14/03/16.
 */
public class Injection {
    public static APIInterface getAPIImp(){
        return new APIImp();
    }
}
