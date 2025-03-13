package modelo.clasesJAXB;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.Double;

public class Adapter extends XmlAdapter<String, Double> {

	@Override
	public Double unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		DecimalFormat formato = new DecimalFormat("#,###.##");
		return Double.valueOf(v);
	}

	@Override
	public String marshal(Double v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
