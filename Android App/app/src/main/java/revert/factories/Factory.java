package revert.factories;

import java.util.Stack;

public abstract class Factory<T> {

    protected Stack<T> items;

    protected Factory() {
        items = new Stack<>();
        for (int i = 0; i < getEstimatedRequired(); i++)
            items.push(createItem());
    }


    public T get()
    {
        if (items.empty())
            for (int i = 0; i < getEstimatedRequired(); i++)
            {
                items.push(createItem());
            }

        return items.pop();
    }

    public void put(T item)
    {
        items.push(cleanItem(item));
    }

    protected abstract int getEstimatedRequired();

    protected abstract T createItem();

    protected abstract T cleanItem(T item);
}
