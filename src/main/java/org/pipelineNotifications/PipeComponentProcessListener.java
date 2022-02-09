package org.pipelineNotifications;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.mule.runtime.api.component.location.LocationPart;
import org.mule.runtime.api.notification.IntegerAction;
import org.mule.runtime.api.notification.PipelineMessageNotification;
import org.mule.runtime.api.notification.PipelineMessageNotificationListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class PipeComponentProcessListener implements PipelineMessageNotificationListener<PipelineMessageNotification> {

	private Logger log = Logger.getLogger(getClass().getName());
	private long startTime = 0;
	private long endTime = 0;
	private HashMap<String, Long> componentStartTimeHolder = new HashMap<String, Long>();

	@SuppressWarnings("deprecation")
	@Value("${notificationOn}")
	String notificationObj;
	@Override
	public void onNotification(PipelineMessageNotification notification) {
		
	
		// System.out.println("1");
		String flowName = notification.getResourceIdentifier();
		// System.out.println("2");

		final IntegerAction action = notification.getAction();
// System.out.println("3");	

		
		
		String process = notification.getEventName();
		
		// System.out.println("4");
		String className = process.getClass().getSimpleName();
		// System.out.println("5");
		//if("1".equalsIgnoreCase(System.getProperty("notificationOn")))
		if("1".equalsIgnoreCase(notificationObj))
		{
			
			if (Integer.parseInt(action.getIdentifier()) == PipelineMessageNotification.PROCESS_START) {
				startTime = System.currentTimeMillis();
				
				System.out.println("*********************PROCESS_START Entry for Flow :" +flowName +  "********************");
				System.out.println("Notification Value " +System.getProperty("notificationOn"));
				System.out.println("Flow Name :" + flowName);
				System.out.println("Action Value " + action.getIdentifier());			
				System.out.println("Process_Start_StartTime  : " + startTime);
				
				componentStartTimeHolder.put(flowName.toString(), startTime);
				//System.out.println(" Start Time " +componentStartTimeHolder.get(flowName.toString()));
				System.out.println("*********************PROCESS_START Exit for Flow :" +flowName +  "********************");
				System.out.println("\n");
			}
			if (Integer.parseInt(action.getIdentifier()) == PipelineMessageNotification.PROCESS_END) {
				
				
				System.out.println("*********************PROCESS_END Entry for Flow :" +flowName +  "********************");
				System.out.println("*********************PROCESS_END Exit for Flow :" +flowName +  "********************");
				System.out.println("\n");

			}

			if (Integer.parseInt(action.getIdentifier()) == PipelineMessageNotification.PROCESS_COMPLETE) {
				
				System.out.println("*********************PROCESS_COMPLETE Entry for Flow :" +flowName +  "********************");
				endTime = System.currentTimeMillis();
				//System.out.println("End Time " +endTime);
				long executionTime = System.currentTimeMillis()
						- (long) componentStartTimeHolder.get(flowName.toString());
				 
				
				System.out.println("Process_Complete_ExecutionTime  :   " + executionTime + " ms");
				System.out.println("\n"
						+ computeDetails(notification.getComponent().getLocation().getParts()) + "\nTotal Time Taken By the " +flowName + " to Complete : "
						+ executionTime + " ms");
			
				System.out.println("*********************PROCESS_COMPLETE Exit for Flow :" +flowName +  "********************");
				System.out.println("\n");

			}
			// System.out.println("6");

			
		}
		
		else {
			
			
		}

	}

	private StringBuilder computeDetails(List<LocationPart> parts) {
		// System.out.println("7");
		StringBuilder computeTime = new StringBuilder("");

		//System.out.println("Parts Size : " + parts.size());

		for (int i = 0; i < parts.size(); i++) {
			//System.out.println("Part value :  [ " + i + " ] " + parts.get(i));
			
				computeTime.append("\nFlow Name : " + parts.get(i).getPartPath() + "\nType : " + parts.get(i).getPartPath()
						+ "\nModule in Flow : " + parts.get(i).getPartPath() + "\nModule Name : "
						+ parts.get(i).getPartIdentifier().get().getIdentifier() + "\nLine Number : "
						+ parts.get(i).getLineInFile());
		
		}
		
		

		
			 
		return computeTime;
	}

}