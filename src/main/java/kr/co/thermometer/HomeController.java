package kr.co.thermometer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JSpinner.ListEditor;

import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.tools.xjc.reader.RawTypeSet.Mode;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
//		String formattedDate = dateFormat.format(date);
//		List<Temperature> listTemperature = new ArrayList<Temperature>();
//		listTemperature = callJpa();
//		int size = listTemperature.size();
//		model.addAttribute("serverTime", formattedDate );
//		model.addAttribute("temperateDate", listTemperature.get(0).getTemperature());
//		model.addAttribute("userId", listTemperature.get(0).getId());
//		model.addAttribute("size", size);
//		model.addAttribute("temperatureList", listTemperature);
		return "home";
	}

	@ResponseBody
	@RequestMapping(value ="listdata", method=RequestMethod.GET, produces="application/json")
	public JSONObject getListData(Locale locale, Model model, HttpServletResponse response, HttpServletRequest request) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		List<Temperature> listTemperature = new ArrayList<Temperature>();
		listTemperature = callJpa();
		int size = listTemperature.size();
		
		JSONArray jsonArr =  new JSONArray();
		for (int i=0; i<listTemperature.size(); i++){
			JSONObject result = new JSONObject();
			result.put("id", listTemperature.get(i).getId().toString());
			result.put("date", listTemperature.get(i).getDate().toString());
			result.put("temperature", listTemperature.get(i).getTemperature().toString());
			jsonArr.add(result);
		}
		
		JSONObject jsonObj = new JSONObject();
		System.out.println(jsonArr);
		jsonObj.put("data", jsonArr);
		return jsonObj;
	}
	
	
	public static List<Temperature> callJpa() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("thermometer");
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득
		List<Temperature> listTemperature = new ArrayList<Temperature>();
		
        try {
            tx.begin(); //트랜잭션 시작
            listTemperature = selectTemperature(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
        return listTemperature;
	}
	
	public static void insertMember(EntityManager em) {
		String id = "id2";
		Member member = new Member();
		member.setId(id);
		member.setUsername("예니");
		member.setAge(2);
		em.persist(member);
	}
	
	public static void selectMember(EntityManager em) {
		String id = "id1";
		//한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

	}

	public static void insertTemperature(EntityManager em) {
		String id = "id1";
		Temperature temperature = new Temperature();
		Date date = new Date();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar time = Calendar.getInstance();
		
		temperature.setId(id);
		temperature.setDate(format.format(time.getTime()));
		temperature.setTemperature(1);
		em.persist(temperature);
		
	}
	
	public static List<Temperature> selectTemperature(EntityManager em) {
		String id = "id1";
		//한 건 조회
		Temperature findTemperature = em.find(Temperature.class, id);
		System.out.println("findTemperature=" + findTemperature.getTemperature() + ", Date=" + findTemperature.getDate());
		
		//목록 조회
		List<Temperature> temperature = em.createQuery("select m from Temperature m", Temperature.class).getResultList();
		System.out.println("temperature.size=" + temperature.size());
		
		return temperature;
	}
}
