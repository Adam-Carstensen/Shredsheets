package revert.factories;

import android.graphics.Rect;

public class RectFactory extends Factory<Rect> {
    public static final RectFactory instance = new RectFactory();

    @Override
    protected int getEstimatedRequired() {
        return 10;
    }

    @Override
    protected Rect createItem() {
        return new Rect();
    }

    @Override
    protected Rect cleanItem(Rect item) {
        item.setEmpty();
        return item;
    }
}
