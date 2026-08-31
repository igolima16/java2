public class TestAuthor {
    public static void main(String[] args) {
        
        Author a1 = new Author("Igo lima", "igolima@gmail.com", 'm');
        System.out.println(a1.toString());

        
        a1.setEmail("novoemail@yahoo.com");
        
        
        System.out.println("Nome: " + a1.getName());
        System.out.println("Email: " + a1.getEmail());
        System.out.println("Gênero: " + a1.getGender());
    }
}