public class TestBook {
    public static void main(String[] args) {
        Author[] authors = new Author[2];
        authors[0] = new Author("Autor 01", "autor01@somewhere.com.br", 'm');
        authors[1] = new Author("Autor 02", "autor02@nowhere.com.br", 'f');
        
        Book testeBook = new Book("Java for Dummy", authors, 19.99, 99);
        
        System.out.println(testeBook.toString());
        System.out.println("Nomes dos autores: " + testeBook.getAuthorNames());
    }
}