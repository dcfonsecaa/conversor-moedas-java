import java.net.http.*;
import java.net.URI;
import com.google.gson.Gson;

public class App {
    public static void main(String[] args) {
        // 🔐 Sua chave da API
        String apiKey = "8004ecaa9740c84a50b0cbe9";
        String base = "USD";
        String target = "BRL";
        double amount = 100;

        // 🌐 Montar a URL do endpoint correto
        String url = String.format(
            "https://v6.exchangerate-api.com/v6/%s/latest/%s",
            apiKey, base
        );

        try {
            // 🧾 Criar requisição HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // 📬 Obter resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 🔍 Interpretar JSON com Gson
            Gson gson = new Gson();
            ConversaoResposta resultado = gson.fromJson(response.body(), ConversaoResposta.class);

            // 📊 Calcular conversão
            double taxa = resultado.conversion_rates.get(target);
            double convertido = taxa * amount;

            // 🖨️ Mostrar resultado
            System.out.println("Taxa de câmbio: " + taxa);
            System.out.println("Conversão de " + amount + " " + base + " para " + target + ": " + convertido);

        } catch (Exception e) {
            System.out.println("Erro ao chamar a API: " + e.getMessage());
        }
    }

    // 👇 Classe interna para mapear o JSON retornado
    static class ConversaoResposta {
        java.util.Map<String, Double> conversion_rates;
    }
}
