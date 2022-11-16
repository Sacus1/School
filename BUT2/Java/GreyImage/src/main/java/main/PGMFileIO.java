package main;
import java.io.*;

// Lecture/ ecriture d'images 8 bits au format PGM

class PGMFileIO {

    private static String MAGIC_PGM = "P5\n";
    private char c;
    private int dimX;
    private int dimY;
    private short[] data;
    private String filename;
    FileInputStream filein;
    FileOutputStream fileout;

    public PGMFileIO(String filename)
    {
        this.filename=filename;
    }

    void readPGM() throws FileNotFoundException,IOException
    {
        filein=new FileInputStream(filename);
        if ( !matchKey(MAGIC_PGM) )
            throw new IOException(filename + " : wrong magic number");
        skipComment('#');
        dimX = getInt();
        dimY = getInt();
        skipLine();
        skipComment('#');
        skipLine();
        data = loadData(dimX * dimY);
        filein.close();
    }

    void writePGM(int dx, int dy, short[] data) throws IOException
    {
        fileout=new FileOutputStream(filename);
        byte[] buffer=new byte[dx*dy];
        for(int i=0; i<dx*dy; i++)
        {
            buffer[i]=(byte)(data[i]);
        }
        put(MAGIC_PGM);
        put("#\n");
        put(dx + " " + dy + "\n255\n");
        fileout.write(buffer);
        fileout.close();
    }

    private void put(String data) throws IOException
    {
        fileout.write(data.getBytes());
    }

    private boolean matchKey(String key) throws IOException
    {
        byte[] buf = new byte[key.length()];
        filein.read(buf, 0, key.length());
        return key.compareTo(new String(buf)) == 0;
    }

    private void getChar() throws IOException
    {
        c = (char)filein.read();
    }

    private int getInt() throws IOException
    {
        String s = "";
        while ( (c != '\n') && Character.isSpaceChar(c) )
            getChar();
        while ( (c != '\n') && !Character.isSpaceChar(c) )
        {
            s = s + c;
            getChar();
        }
        return Integer.parseInt(s);
    }

    private void skipLine() throws IOException
    {
        while ( c != '\n' )
            getChar();
    }

    private void skipComment(char code) throws IOException
    {
        getChar();
        while ( c == code )
        {
            skipLine();
            getChar();
        }
    }

    private short[] loadData(int size) throws IOException
    {
        byte[] data = new byte[size];
        filein.read(data, 0, size);
        short[] res=new short[size];

        for(int i=0; i<size; i++)
            res[i]=(short)(data[i] & 0xFF) ; //unsighed !
        return res;
    }

    public int getSizeX() {return dimX;}
    public int getSizeY() {return dimY;}
    public short[] getData() {return data;}
}
