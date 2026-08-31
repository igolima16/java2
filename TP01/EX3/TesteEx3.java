public class TesteEx3 {
    public static void main(String[] args) {
        Student student = new Student("João", "Rua A, 123", "Análise de Sistemas", 2026, 500.0);
        Staff staff = new Staff("Maria", "Av B, 456", "IFSP", 3500.0);

        System.out.println("--- Testando Student ---");
        System.out.println(student.toString());
        student.setYear(2027);
        student.setAddress("Rua A, 321");
        System.out.println("Ano atualizado: " + student.getYear());
        
        System.out.println("\n--- Testando Staff ---");
        System.out.println(staff.toString());
        staff.setPay(3800.0);
        System.out.println("Salário atualizado: " + staff.getPay());
    }
}
