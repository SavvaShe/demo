package Settings.Protocols;

public class ProtocolException extends Exception
{
	private String ReturnStatus;
	private String ReturnMessage;
	
	public ProtocolException()
	{
		ReturnStatus = "FAIL";
		ReturnMessage = "При обращении к серверу произошла ошибка.";
	}
	public ProtocolException(String nReturnMessage)
	{
		ReturnStatus = "FAIL";
		ReturnMessage = nReturnMessage;
	}
	public ProtocolException(String nReturnStatus, String nReturnMessage)
	{
		ReturnStatus = nReturnStatus;
		ReturnMessage = nReturnMessage;
	}
	public String getStatus ()
	{
		return ReturnStatus;
	}
	public String getMessage ()
	{
		return ReturnMessage;
	}
}
