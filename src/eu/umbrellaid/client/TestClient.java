package eu.umbrellaid.client;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import eu.umbrellaid.entity.Address;
import eu.umbrellaid.entity.Gender;
import eu.umbrellaid.service.ChallengeResponse;
import eu.umbrellaid.service.impl.ChallengeResponseImpl;

public class TestClient {

	public final static String URL = "http://localhost:8080/challenge/challenge";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// initialize address
			Address address = new Address();
			address.setFirstName("Bjšrn");
			address.setMiddleInitial("E");
			address.setLastName("Abt");
			address.setEmail("bjoern.abt@object.ch");
			address.setGender(Gender.MALE);
			address.setNationality("SE");
			address.setPhone("+41563103509");
			address.setEaahash("abcd1234");
			address.setEaakey("1234abcd");

			// Create HttpClient Object
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(URL);

			// Create Challenge
			ChallengeResponse cr = new ChallengeResponseImpl();
			String challenge = cr.generateChallenge();
			System.out.println(challenge + " | " + address.getEaahash());

			// Build Request with challenge
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("EAAChallenge", challenge));
			formparams.add(new BasicNameValuePair("EAAHash", address.getEaahash()));
			post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			HttpResponse response = httpclient.execute(post);

			// Get the response from the remote endpoint
			for (Header header : response.getAllHeaders()) {
			}
			String resp = IOUtils.toString(response.getEntity().getContent());
			System.out.println(resp);
			String rand = response.getFirstHeader("EAARAND").getValue();
			String clientresult = response.getFirstHeader("EAAResult").getValue();
			System.out.println("Clientresult: " + clientresult);
			
			// calculate result
			String result = cr.calculate(address.getEaakey(), challenge, rand);
			System.out.println("Result: " + result);

			// if the results are the same
			if (result.equals(clientresult)) {
				BeanInfo bi = Introspector.getBeanInfo(address.getClass());
				PropertyDescriptor[] pd = bi.getPropertyDescriptors();

				// create the attributes
				formparams = new ArrayList<NameValuePair>();
				System.out.println("Result: " + result);
				formparams.add(new BasicNameValuePair("EAAResult", result));
				for (int i = 0; i < pd.length; i++) {
					Method readMethod = pd[i].getReadMethod();
					try {
						String value = readMethod.invoke(address, (Object[]) null).toString();
						formparams.add(new BasicNameValuePair(pd[i].getDisplayName(), value));
					} catch (Exception e) {
					}
				}

				post = new HttpPost(URL);
				
				// and send them
				post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));

				// Check code
				response = httpclient.execute(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
