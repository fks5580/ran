package textbook.naverAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NaverSearchAPI {
		
    public String Search(String word) {
        String clientId = "NhEDrGuvXvRCwueSRLso";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "zdXGuFO3aJ";//애플리케이션 클라이언트 시크릿값";
        StringBuffer response = null;
        try {
            String text = URLEncoder.encode(word, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book.json?query="+ text; // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            // 정상 호출
            if(responseCode==200) { 
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            // 에러 발생
            } else { 
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            
        } catch (Exception e) {
            System.out.println("Search()메소드에서 오류  : " + e);
        }
        return response.toString();
    }
}