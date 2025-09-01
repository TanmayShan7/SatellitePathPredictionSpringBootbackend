package com.main.SatellitePathPredictionSpringBoot.Others;

import java.util.ArrayList;
import java.util.List;

import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;

//import com.github.amsacode.predict4java.TLE;

public class SatelliteOrbitGenerator_OLD {

//	public String orbitGenerator(String name, String Line1, String Line2) throws IOException {
//
//		System.out.println(name);
//		System.out.println(Line1);
//		System.out.println(Line2);
//
//		String tleData = name + "\n" + Line1 + "\n" + Line2 + "\n";
//		InputStream is = new ByteArrayInputStream(tleData.getBytes());
//		List<TLE> tleList = TLE.importSat(is);
//		 String linestring = null;
//		if (!tleList.isEmpty()) {
//
//			List<String> coords = new ArrayList<>();
//			Date now = new Date();
//			Calendar cal = Calendar.getInstance();
//			
//			TLE tle = new TLE(tleList.get(0));
//			Satellite satellite = SatelliteFactory.createSatellite(tle);
////			GroundStationPosition gsp = new GroundStationPosition(12.9716, 77.5946, 920);
//			GroundStationPosition gsp = new GroundStationPosition(0,0,0);
//			SatPos postion = satellite.getPosition(gsp, new Date());
//			
//			for (int i = 0; i < 1440; i++) {
//				
//				 cal.setTime(now);
//                 cal.add(Calendar.MINUTE, i);
//                 SatPos pos = satellite.getPosition(gsp, cal.getTime());
//
//                 double lat = pos.getLatitude();
//                 double lon = pos.getLongitude();
//                 if (lon > 180) lon -= 360;
//                 if (lon < -180) lon += 360;
//
//                 if (Double.isFinite(lat) && Double.isFinite(lon)) {
//                     coords.add(String.format("%.6f %.6f", lon, lat));
//                 }
//                 
//              
////                 System.out.println("##   "+linestring);
//				
//			}
//			   linestring = "LINESTRING(" + String.join(", ", coords) + ")";
//
//		}
//		return linestring;
//
//	}

//	public String orbitGenerator(String name, String line1, String line2) throws IOException {
//	    System.out.println(name);
//	    System.out.println(line1);
//	    System.out.println(line2);
//
//	    String tleData = name + "\n" + line1 + "\n" + line2 + "\n";
//	    InputStream is = new ByteArrayInputStream(tleData.getBytes());
//	    List<TLE> tleList = TLE.importSat(is);
//
//	    String lineString = null;
//
//	    if (!tleList.isEmpty()) {
//	        List<String> coords = new ArrayList<>();
//	        TLE tle = new TLE(tleList.get(0));
//	        Satellite satellite = SatelliteFactory.createSatellite(tle);
//
//	        // Dummy ground station (required by predict4java)
//	        GroundStationPosition dummyGSP = new GroundStationPosition(0.0, 0.0, 0.0);
//
//	        // Start from current time
//	        Calendar calendar = Calendar.getInstance();
//	        Date startTime = calendar.getTime();
//
//	        // 1440 points (1 per minute for 24 hours)
//	        for (int i = 0; i < 1440; i++) {
//	            calendar.setTime(startTime);
//	            calendar.add(Calendar.MINUTE, i);
//	            Date currentTime = calendar.getTime();
//
//	            SatPos pos = satellite.getPosition(dummyGSP, currentTime);
//
//	            double lat = pos.getLatitude();
//	            double lon = pos.getLongitude();
//
//	            // Optional: Normalize to [-180, 180] to avoid jumps
//	            if (lon > 180) lon -= 360;
//	            if (lon < -180) lon += 360;
//
//	            if (Double.isFinite(lat) && Double.isFinite(lon)) {
//	                coords.add(String.format("%.6f %.6f", lon, lat));
//	            }
//	        }
//
//	        // Convert to WKT
//	        lineString = "LINESTRING(" + String.join(", ", coords) + ")";
//	    }
//
//	    return lineString;
//	}

//	public String orbitGenerator(String name, String line1, String line2) throws Exception {
//		// Initialize Orekit if not already done
//
//		String tleData = name + "\n" + line1 + "\n" + line2 + "\n";
//		InputStream is = new ByteArrayInputStream(tleData.getBytes());
//		List<TLE> tleList = TLE.importSat(is);
//		TLE tle = new TLE(tleList.get(0));
//
//		TLEPropagator propagator = TLEPropagator.selectExtrapolator(tle);
//
//		// Prepare date/time
//		AbsoluteDate startDate = tle.get
//		Frame earthFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
//		GeodeticPoint geoPoint;
//		OneAxisEllipsoid earth = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
//				Constants.WGS84_EARTH_FLATTENING, earthFrame);
//
//		List<String> coords = new ArrayList<>();
//
//		for (int i = 0; i < 1440; i++) {
//			AbsoluteDate currentDate = startDate.shiftedBy(i * 60.0); // Every minute
//			SpacecraftState state = propagator.propagate(currentDate);
//			PVCoordinates pvCoordinates = state.getPVCoordinates(earthFrame);
//			Vector3D position = pvCoordinates.getPosition();
//
//			geoPoint = earth.transform(position, earthFrame, currentDate);
//			double lat = Math.toDegrees(geoPoint.getLatitude());
//			double lon = Math.toDegrees(geoPoint.getLongitude());
//
//			// Normalize longitudes
//			if (lon > 180)
//				lon -= 360;
//			if (lon < -180)
//				lon += 360;
//
//			if (Double.isFinite(lat) && Double.isFinite(lon)) {
//				coords.add(String.format("%.6f %.6f", lon, lat));
//			}
//		}
//
//		String lineString = "LINESTRING(" + String.join(", ", coords) + ")";
//		return lineString;
//	}
	
	
	
//	public String orbitGenerator(String name, String line1, String line2) throws Exception {
//	    // Prepare TLE
//	    String tleData = name + "\n" + line1 + "\n" + line2 + "\n";
//	    InputStream is = new ByteArrayInputStream(tleData.getBytes());
//	    List<TLE> tleList = TLE.importSat(is);
//	    TLE tle = new TLE(tleList.get(0));
//
//	    // Use correct initial date method
//	    AbsoluteDate startDate = tle.getInitialDate();
//
//	    // Initialize propagator
//	    TLEPropagator propagator = TLEPropagator.selectExtrapolator(tle);
//
//	    // Earth model
//	    Frame earthFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
//	    OneAxisEllipsoid earth = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
//	                                                   Constants.WGS84_EARTH_FLATTENING,
//	                                                   earthFrame);
//
//	    List<String> coords = new ArrayList<>();
//
//	    for (int i = 0; i < 1440; i++) {
//	        AbsoluteDate currentDate = startDate.shiftedBy(i * 60.0);
//	        SpacecraftState state = propagator.propagate(currentDate);
//	        PVCoordinates pvCoordinates = state.getPVCoordinates(earthFrame);
//	        Vector3D position = pvCoordinates.getPosition();
//
//	        GeodeticPoint geoPoint = earth.transform(position, earthFrame, currentDate);
//	        double lat = Math.toDegrees(geoPoint.getLatitude());
//	        double lon = Math.toDegrees(geoPoint.getLongitude());
//
//	        // Normalize longitude
//	        if (lon > 180) lon -= 360;
//	        if (lon < -180) lon += 360;
//
//	        if (Double.isFinite(lat) && Double.isFinite(lon)) {
//	            coords.add(String.format("%.6f %.6f", lon, lat));
//	        }
//	    }
//
//	    return "LINESTRING(" + String.join(", ", coords) + ")";
//	}
	
	
	
	public String orbitGenerator(String name, String line1, String line2) throws Exception {
	    TLE tle = new TLE(line1, line2); // ✅ Use Orekit's TLE constructor

	    TLEPropagator propagator = TLEPropagator.selectExtrapolator(tle);
	    AbsoluteDate startDate = tle.getDate(); // ✅ This will now work

	    Frame earthFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
	    OneAxisEllipsoid earth = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
	            Constants.WGS84_EARTH_FLATTENING, earthFrame);

	    List<String> coords = new ArrayList<>();

	    for (int i = 0; i < 1440; i++) {
	        AbsoluteDate currentDate = startDate.shiftedBy(i * 60.0); // 1-minute step
	        SpacecraftState state = propagator.propagate(currentDate);
	        PVCoordinates pvCoordinates = state.getPVCoordinates(earthFrame);
	        Vector3D position = pvCoordinates.getPosition();

	        GeodeticPoint geoPoint = earth.transform(position, earthFrame, currentDate);
	        double lat = Math.toDegrees(geoPoint.getLatitude());
	        double lon = Math.toDegrees(geoPoint.getLongitude());

	        if (lon > 180) lon -= 360;
	        if (lon < -180) lon += 360;

	        if (Double.isFinite(lat) && Double.isFinite(lon)) {
	            coords.add(String.format("%.6f %.6f", lon, lat));
	        }
	    }
	    String linestring ="LINESTRING(" + String.join(", ", coords) + ")";
//	    return "LINESTRING(" + String.join(", ", coords) + ")";
	    return linestring;
	}  	


}
