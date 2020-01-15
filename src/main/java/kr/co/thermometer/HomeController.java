package kr.co.thermometer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
		
		return "home";
	}

	@ResponseBody
	@RequestMapping(value ="listdata", method=RequestMethod.GET, produces="application/json")
	public JSONObject getListData(Locale locale, Model model, HttpServletResponse response, HttpServletRequest request) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		List<Temperature> listTemperature = new ArrayList<Temperature>();
		// 우선 member를 중복되지 않게 구한다.  얘는 무조건 중복 없음
		List<Member> listMember = new ArrayList<Member>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("function", "selectMember");
		listMember = (List<Member>) callJpa(map);
		int size = listTemperature.size();
		
		JSONArray jsonArr =  new JSONArray();
		
		// 중복되지 않은 멤버들로 일주일 양을 구한다. 
		// member를 for문 돌면서 일주일치 모두를 구한뒤 map list 형태로 구한다. 
		// 일주일의 날짜 데이터를 가지고 가서 매핑 시켜준다. vs 일주일의 데이터를 없으면 없게 넣어줘서 가져간다.?
		
		for (int i=0; i<listMember.size(); i++){
			JSONObject data = new JSONObject();
			String id = listMember.get(i).getId();
			Map<String, String> param = new HashMap<String, String>();
			param.put("people", "more");
			
			param.put("id", id);
			param.put("function", "selectTemperature");
			// list<temperature>
			List<Temperature> x =  (List<Temperature>) callJpa(param);
			jsonArr.add(x);
		}
			
	
			
//			Map<String, List<Map<String,String>>> list = new HashMap<String, List<Map<String,String>>>();
//			
//			Map<String, List<String>> listColumn = new Map<String, List<String>>();
//			
//			result.put("id", listTemperature.get(i).getId().toString());
//			result.put("date", listTemperature.get(i).getDate().toString());
//			result.put("temperature", listTemperature.get(i).getTemperature().toString());
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", jsonArr);
		return jsonObj;
	}
	
	
	public static Object callJpa(Map<String, String> map) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("thermometer");
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득
		Object result = new Object();
		
        try {
            tx.begin(); //트랜잭션 시작
//            listTemperature = selectTemperature(em);  //비즈니스 로직
            result = logic(map, em);
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
        return result;
	}
	
	public static Object logic(Map<String,String> param, EntityManager em) {
		String function = (String) param.get("function");
		if (function.equals("selectMember")) {
			return selectMember(em);
		} else {
			return selectTemperature(em, param );
		}
	}
	
	public static void insertMember(EntityManager em) {
		String id = "id2";
		Member member = new Member();
		member.setId(id);
		member.setUsername("예니");
		member.setAge(2);
		em.persist(member);
	}
	
	public static List<Member> selectMember(EntityManager em) {
		String id = "id1";
		//한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());

        return members;
	}

	public static void insertTemperature(EntityManager em, Map<String, String> param) {
		String id = param.get("id");
		Temperature temperature = new Temperature();
		Date date = new Date();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar time = Calendar.getInstance();
		
		temperature.setId(id);
		temperature.setDate(format.format(time.getTime()));
		temperature.setTemperature(1);
		em.persist(temperature);
		
	}
	/**
	 * 
	 * @param em
	 * @param param
	 *  id - 찾으려는 id의 온도 리스트 
	 *  people - one or more one이면 한사람만, more이면 여러사람 
	 * @return
	 */
	public static List<Temperature> selectTemperature(EntityManager em, Map<String, String> param) {
		String id = param.get("id");
		//한 건 조회
		List<Temperature> temperature = new ArrayList<Temperature>();
		if (param.get("people").equals("one")) {
			Temperature oneData = em.find(Temperature.class, id);
			temperature = new ArrayList<Temperature>();
			temperature.add(oneData);
			System.out.println("findTemperature=" + temperature.get(0).getTemperature() 
					+ ", Date=" + temperature.get(0).getDate());
		} else {
			//목록 조회
			TypedQuery<Temperature> query = em.createQuery("" + "select m from Temperature m where m.id = :id", Temperature.class);
			query.setParameter("id", id);
			temperature = query.getResultList();
			
//			temperature = em.createQuery("select m from Temperature m m.id = :id", Temperature.class).getResultList();
			
			System.out.println("temperature.size=" + temperature.size());
		}
		return temperature;
	}
}
