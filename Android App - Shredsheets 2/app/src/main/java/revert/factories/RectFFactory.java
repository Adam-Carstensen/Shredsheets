package revert.factories;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class RectFFactory extends Factory<RectF> {

    public static RectFFactory instance = new RectFFactory();

    @Override
    protected int getEstimatedRequired() {
        return 1000;
    }

    @Override
    protected RectF createItem() {
        return new RectF();
    }

    @Override
    protected RectF cleanItem(RectF item) {
        item.set(0, 0, 0, 0);
        return item;
    }

    public RectF get(float left, float top, float right, float bottom)
    {
        if (items.empty()) {
            Log.v("Adam - RectFFactory", "1000 rects created");
            for (int i = 0; i < 1000; i++) {
                items.push(createItem());
            }
        }

        RectF rect = items.pop();
        rect.set(left, top, right, bottom);
        return rect;
    }

    public RectF get(RectF item)
    {
        if (items.empty()) {
            Log.v("Adam - RectFFactory", "1000 rects created");
            for (int i = 0; i < 1000; i++) {
                items.push(createItem());
            }
        }

        RectF rect = items.pop();
        rect.set(item.left, item.top, item.right, item.bottom);
        return rect;
    }

    public RectF get(Rect item) {
        if (items.empty()) {
            Log.v("Adam - RectFFactory", "1000 rects created");
            for (int i = 0; i < 1000; i++)
                items.push(createItem());
        }

        RectF rect = items.pop();
        rect.set(item.left, item.top, item.right, item.bottom);
        return rect;
    }
}
