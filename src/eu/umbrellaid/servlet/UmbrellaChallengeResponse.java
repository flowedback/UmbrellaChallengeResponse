package eu.umbrellaid.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eu.umbrellaid.entity.Address;
import eu.umbrellaid.entity.Gender;
import eu.umbrellaid.entity.Honorific;
import eu.umbrellaid.service.AttributeWrite;
import eu.umbrellaid.service.ChallengeResponse;
import eu.umbrellaid.service.KeyLookup;
import eu.umbrellaid.service.impl.ChallengeResponseImpl;

public final class UmbrellaChallengeResponse extends HttpServlet {

	/**
	 * 
	 */

	public static String EAA_HASH = "EAAHash";

	public static String EAA_CHALLENGE = "EAAChallenge";

	public static String EAA_RAND = "EAARAND";

	public static String EAA_RESULT = "EAAResult";

	private static final long serialVersionUID = 5821350025490673625L;

	private Random rd;

	private KeyLookup keylookup;

	private AttributeWrite attributewrite;

	private ChallengeResponse challenge = new ChallengeResponseImpl();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String arw = config.getInitParameter("attributewriter");
		String klp = config.getInitParameter("keylookup");

		// initialize parameters
		rd = new Random();
		try {
			keylookup = (KeyLookup) Class.forName(klp).newInstance();
			attributewrite = (AttributeWrite) Class.forName(arw).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAction(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAction(req, resp);
	}

	private void doAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Get session object
		HttpSession session = req.getSession(true);

		// get parameters
		String eaahash = req.getParameter(UmbrellaChallengeResponse.EAA_HASH);
		String eaachallenge = req.getParameter(UmbrellaChallengeResponse.EAA_CHALLENGE);

		// if eaahash and eaachallenge are defined
		if (eaahash != null && !eaahash.equals("") && eaachallenge != null && !eaachallenge.equals("")) {

			// Generate the result from the Keylookup and a random
			String rand = new Integer(rd.nextInt(1000000000)).toString();
			String key = keylookup.lookup(eaahash);
			String result = challenge.calculate(key, eaachallenge, rand);

			// Set the headers correspondingly
			resp.setHeader(UmbrellaChallengeResponse.EAA_RAND, rand);
			resp.setHeader(UmbrellaChallengeResponse.EAA_RESULT, result);

			// Store the result in the session
			session.setAttribute(UmbrellaChallengeResponse.EAA_RESULT, result);

		} else {
			if (req.getParameter(UmbrellaChallengeResponse.EAA_RESULT) != null
					&& req.getParameter(UmbrellaChallengeResponse.EAA_RESULT).equals(session.getAttribute(UmbrellaChallengeResponse.EAA_RESULT))) {
				String email = req.getParameter("email");
				String affiliation = req.getParameter("affiliation");
				String firstname = req.getParameter("firstName");
				String gender = req.getParameter("gender");
				String lastname = req.getParameter("lastName");
				String middleinitial = req.getParameter("middleInitial");
				String nationality = req.getParameter("nationality");
				String phone = req.getParameter("phone");
				String title = req.getParameter("title");

				Address address = new Address();
				address.setEmail(email);
				address.setAffiliation(affiliation);
				address.setFirstName(firstname);
				if (gender != null && !gender.equals("")) {
					try {
						address.setGender(Gender.valueOf(gender));
					} catch (Exception e) {
					}
				}
				address.setLastName(lastname);
				address.setMiddleInitial(middleinitial);
				address.setNationality(nationality);
				address.setPhone(phone);
				if (title != null && !title.equals("")) {
					try {
						address.setTitle(Honorific.valueOf(title));
					} catch (Exception e) {
					}
				}

				attributewrite.write(address);

			}
		}

	}
}
