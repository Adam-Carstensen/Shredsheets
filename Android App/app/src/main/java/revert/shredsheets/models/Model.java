package revert.shredsheets.models;

import android.content.Context;

public abstract class Model {

    public Context context;
    public Model(Context context)
    {
        this.context = context;
    }


}
