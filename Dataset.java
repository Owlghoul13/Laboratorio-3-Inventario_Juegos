import java.util.*;
public class Dataset {
    private ArrayList<Game> data;
    private String sortedByAttribute;
    public Dataset(ArrayList<Game> data) {
        this.data = data;
        this.sortedByAttribute = "";
    }

    public ArrayList<Game> getGamesByPrice(int price) {
        ArrayList<Game> result = new ArrayList<>();
        if ("price".equals(sortedByAttribute)) {
            int index = Collections.binarySearch(data, new Game("", "",
                    price, 0), Comparator.comparingInt(Game::getPrice));
            if (index >= 0) {
                int left = index, right = index;
                while (left > 0 && data.get(left - 1).getPrice() == price) left--;
                while (right < data.size() - 1 && data.get(right + 1).getPrice() == price) right++;
                for (int i = left; i <= right; i++) result.add(data.get(i));
            }
        } else {
            for (Game g : data) if (g.getPrice() == price) result.add(g);
        }
        return result;
    }

    public ArrayList<Game> getGamesByPriceRange(int lower, int higher) {
        ArrayList<Game> result = new ArrayList<>();
        for (Game g : data) {
            if (g.getPrice() >= lower && g.getPrice() <= higher) result.add(g);
            if ("price".equals(sortedByAttribute) && g.getPrice() > higher) break;
        }
        return result;
    }

    public ArrayList<Game> getGamesByCategory(String category) {
        ArrayList<Game> result = new ArrayList<>();
        for (Game g : data) if (g.getCategory().equals(category)) result.add(g);
        return result;
    }

    public ArrayList<Game> getGamesByQuality(int quality) {
        ArrayList<Game> result = new ArrayList<>();
        for (Game g : data) if (g.getQuality() == quality) result.add(g);
        return result;
    }

    public ArrayList<Game> sortByAlgorithm(String algorithm, String attribute) {
        Comparator<Game> comparator = switch (attribute) {
            case "price" -> Comparator.comparingInt(Game::getPrice);
            case "category" -> Comparator.comparing(Game::getCategory);
            case "quality" -> Comparator.comparingInt(Game::getQuality);
            default -> Comparator.comparingInt(Game::getPrice);
        };

        switch (algorithm) {
            case "bubbleSort" -> bubbleSort(comparator);
            case "insertionSort" -> insertionSort(comparator);
            case "selectionSort" -> selectionSort(comparator);
            case "mergeSort" -> data = mergeSort(data, comparator);
            case "quickSort" -> quickSort(0, data.size() - 1, comparator);
            default -> data.sort(comparator);
        }

        sortedByAttribute = attribute;
        return data;
    }

    private void bubbleSort(Comparator<Game> comparator) {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size() - i - 1; j++) {
                if (comparator.compare(data.get(j), data.get(j + 1)) > 0) {
                    Collections.swap(data, j, j + 1);
                }
            }
        }
    }

    private void insertionSort(Comparator<Game> comparator) {
        for (int i = 1; i < data.size(); i++) {
            Game key = data.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(data.get(j), key) > 0) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, key);
        }
    }

    private void selectionSort(Comparator<Game> comparator) {
        for (int i = 0; i < data.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.size(); j++) {
                if (comparator.compare(data.get(j), data.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(data, i, minIndex);
        }
    }

    private ArrayList<Game> mergeSort(List<Game> list, Comparator<Game> comparator) {
        if (list.size() <= 1) return new ArrayList<>(list);
        int mid = list.size() / 2;
        List<Game> left = mergeSort(list.subList(0, mid), comparator);
        List<Game> right = mergeSort(list.subList(mid, list.size()), comparator);
        return merge(left, right, comparator);
    }

    private ArrayList<Game> merge(List<Game> left, List<Game> right, Comparator<Game> comparator) {
        ArrayList<Game> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        return result;
    }

    private void quickSort(int low, int high, Comparator<Game> comparator) {
        if (low < high) {
            int pi = partition(low, high, comparator);
            quickSort(low, pi - 1, comparator);
            quickSort(pi + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<Game> comparator) {
        Game pivot = data.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(data.get(j), pivot) < 0) {
                i++;
                Collections.swap(data, i, j);
            }
        }
        Collections.swap(data, i + 1, high);
        return i + 1;
    }
}
