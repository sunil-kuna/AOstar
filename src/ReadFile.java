import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ReadFile {

	String path;
	public ReadFile(String file_path) {
		// TODO Auto-generated constructor stub
		path=file_path;
	}
	public int no_of_nodes() throws IOException
	{
		FileReader fr=new FileReader(path);
		BufferedReader br=new BufferedReader(fr);
		int i=0;
		String temp;

		
		while((temp=br.readLine())!=null)
		{
			i++;
		}
		return i;
	}
	
	public String openfile() throws IOException
	{
		FileReader fr=new FileReader(path);
		BufferedReader br=new BufferedReader(fr);
		int i=0;
		String data = null;
		String temp;
		while((temp=br.readLine())!=null)
		{
			data+=temp;
			data+="\n";
		}
		return data;
		
	}

}
