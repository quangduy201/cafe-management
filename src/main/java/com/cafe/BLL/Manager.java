package com.cafe.BLL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public abstract class Manager<T> {
    public Object[][] getData(List<T> objectList) {
        Object[][] data = new Object[objectList.size()][];
        for (int i = 0; i < data.length; ++i) {
            data[i] = objectList.get(i).toString().split(" \\| ");
        }
        return data;
    }

    public String getAutoID(String type, int digits, List<T> objectList) {
        int count = 0;
        if (objectList.size() == 0) {
            return type + formatNumberToString(1, digits);
        }

        T lastObject = objectList.get(objectList.size() - 1);
        if (lastObject.toString().split(" \\| ")[0]
            .equals(type + formatNumberToString(objectList.size(), digits))) {
            count += objectList.size();
        } else {
            while (objectList.get(count).toString().split(" \\| ")[0]
                .equals(type + formatNumberToString(count + 1, digits)))
                count++;
        }
        return type + formatNumberToString(count + 1, digits);
    }

    public String formatNumberToString(int number, int digits) {
        String format = "%0" + digits + "d";
        return String.format(format, number);
    }

    public List<Object> getObjectsProperty(String key, List<T> objectList) {
        List<Object> listOfProperties = new ArrayList<>();
        for (T object : objectList) {
            listOfProperties.add(getValueByKey(object, key));
        }
        return listOfProperties;
    }

    public List<T> findObjectsBy(String key, Object value, List<T> objectList) {
        List<T> objects = new ArrayList<>();
        for (T object : objectList)
            if (getValueByKey(object, key).equals(value))
                objects.add(object);
        return objects;
    }

    public int getIndex(T object, String key, List<T> objectList) {
        return IntStream.range(0, objectList.size())
            .filter(i -> Objects.equals(getValueByKey(objectList.get(i), key), getValueByKey(object, key)))
            .findFirst()
            .orElse(-1);
    }

    public abstract Object getValueByKey(T object, String key);

//    public abstract Object getValueByKey(Decentralization decentralization, String key);
}
