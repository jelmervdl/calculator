package http;

public interface RequestHandler
{
	public void respond(Request request, Response response) throws Exception;
}
