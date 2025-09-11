package cart;

public class Book {
    private final long id;
    private final String title;
    private final String author;
    private int totalCopies;
    private int availableCopies;

    public Book(long id, String title, String author, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void reduceAvailableCopies(int quantity) {
        if (quantity > availableCopies) {
            throw new IllegalArgumentException("Not enough copies available");
        }
        this.availableCopies -= quantity;
    }
}
