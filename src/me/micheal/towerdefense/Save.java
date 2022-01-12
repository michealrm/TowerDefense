package me.micheal.towerdefense;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;

public class Save {
	static int count = 0;
	public static String getAppdata(){
		String workingDirectory;
		//here, we assign the name of the OS, according to Java, to a variable...
		String OS = (System.getProperty("os.name")).toUpperCase();
		//to determine what the workingDirectory is.
		//if it is some version of Windows
		if (OS.contains("WIN")){
		    //it is simply the location of the "AppData" folder
		    workingDirectory = System.getenv("AppData");
		}
		//Otherwise, we assume Linux or Mac
		else{
		    //in either case, we would start in the user's home directory
		    workingDirectory = System.getProperty("user.home");
		    //if we are on a Mac, we are not done, we look for "Application Support"
		    workingDirectory += "/Library/Application Support";
		}
		//we are now free to set the workingDirectory to the subdirectory that is our 
		//folder.
		return workingDirectory+"/";
	}
	public static void checkSavePath(){
		String wd = getAppdata()+"Tower Defense";
		if(new File(wd).isDirectory())
			Log.log("Appdata/Tower Defense exists.");
		else{
			Log.log("Appdata/Tower Defense does not exist.");
			if(!new File(wd).mkdir())
				Log.err(new Exception("Save location cannot be created"));
			Log.log("Created save folder.");
			try {
				Log.log("Transferring files to save path.");
				copyDirectory(Paths.get(""),Paths.get(wd));
			} catch (IOException e) {
				Log.err(e);
			}
		}
	}
	public static String[] saveValues(){
		if(count<1)
			checkSavePath();
		File f = new File(getAppdata()+"Tower Defense/save.dat");
		if(!f.exists())
			Log.err(new Exception("Can't find save file."));
		else{
			ArrayList<String> n = new ArrayList<String>();
			Scanner in = null;
			try {
				in = new Scanner(f);
			} catch (FileNotFoundException e) {
				Log.err(e);
			}
			while(in.hasNextLine()){
				String take = in.nextLine();
				char[] temp = take.toCharArray();
				if(temp[0]=='-')
					n.add(take);
			}
			/*
			 * CONFIG SAVE VALUES HERE
			 */
			String[] vars = new String[1];
			for(int i = 0;i<n.size();i++){
				if(n.get(i).contains("map="))
					vars[0] = n.get(i).substring(n.get(i).indexOf("="));
			}
		}
		return null;
	}
	private static void copyDirectory(final Path source, final Path target)throws IOException {
		if(count < 1){
	    Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
	        Integer.MAX_VALUE, new FileVisitor<Path>() {

	            @Override
	            public FileVisitResult preVisitDirectory(Path dir,
	                    BasicFileAttributes sourceBasic) throws IOException {
	                Path targetDir = Files.createDirectories(target
	                    .resolve(source.relativize(dir)));
	                AclFileAttributeView acl = Files.getFileAttributeView(dir,
	                    AclFileAttributeView.class);
	                if (acl != null)
	                    Files.getFileAttributeView(targetDir,
	                        AclFileAttributeView.class).setAcl(acl.getAcl());
	                DosFileAttributeView dosAttrs = Files.getFileAttributeView(
	                    dir, DosFileAttributeView.class);
	                if (dosAttrs != null) {
	                    DosFileAttributes sourceDosAttrs = dosAttrs
	                        .readAttributes();
	                    DosFileAttributeView targetDosAttrs = Files
	                        .getFileAttributeView(targetDir,
	                            DosFileAttributeView.class);
	                    targetDosAttrs.setArchive(sourceDosAttrs.isArchive());
	                    targetDosAttrs.setHidden(sourceDosAttrs.isHidden());
	                    targetDosAttrs.setReadOnly(sourceDosAttrs.isReadOnly());
	                    targetDosAttrs.setSystem(sourceDosAttrs.isSystem());
	                }
	                FileOwnerAttributeView ownerAttrs = Files
	                    .getFileAttributeView(dir, FileOwnerAttributeView.class);
	                if (ownerAttrs != null) {
	                    FileOwnerAttributeView targetOwner = Files
	                        .getFileAttributeView(targetDir,
	                            FileOwnerAttributeView.class);
	                    targetOwner.setOwner(ownerAttrs.getOwner());
	                }
	                PosixFileAttributeView posixAttrs = Files
	                    .getFileAttributeView(dir, PosixFileAttributeView.class);
	                if (posixAttrs != null) {
	                    PosixFileAttributes sourcePosix = posixAttrs
	                        .readAttributes();
	                    PosixFileAttributeView targetPosix = Files
	                        .getFileAttributeView(targetDir,
	                            PosixFileAttributeView.class);
	                    targetPosix.setPermissions(sourcePosix.permissions());
	                    targetPosix.setGroup(sourcePosix.group());
	                }
	                UserDefinedFileAttributeView userAttrs = Files
	                    .getFileAttributeView(dir,
	                        UserDefinedFileAttributeView.class);
	                if (userAttrs != null) {
	                    UserDefinedFileAttributeView targetUser = Files
	                        .getFileAttributeView(targetDir,
	                            UserDefinedFileAttributeView.class);
	                    for (String key : userAttrs.list()) {
	                        ByteBuffer buffer = ByteBuffer.allocate(userAttrs
	                            .size(key));
	                        userAttrs.read(key, buffer);
	                        buffer.flip();
	                        targetUser.write(key, buffer);
	                    }
	                }
	                // Must be done last, otherwise last-modified time may be
	                // wrong
	                BasicFileAttributeView targetBasic = Files
	                    .getFileAttributeView(targetDir,
	                        BasicFileAttributeView.class);
	                targetBasic.setTimes(sourceBasic.lastModifiedTime(),
	                    sourceBasic.lastAccessTime(),
	                    sourceBasic.creationTime());
	                return FileVisitResult.CONTINUE;
	            }

	            @Override
	            public FileVisitResult visitFile(Path file,
	                    BasicFileAttributes attrs) throws IOException {
	                Files.copy(file, target.resolve(source.relativize(file)),
	                    StandardCopyOption.COPY_ATTRIBUTES);
	                return FileVisitResult.CONTINUE;
	            }

	            @Override
	            public FileVisitResult
	                    visitFileFailed(Path file, IOException e)
	                            throws IOException {
	                throw e;
	            }

	            @Override
	            public FileVisitResult postVisitDirectory(Path dir,
	                    IOException e) throws IOException {
	                if (e != null) throw e;
	                return FileVisitResult.CONTINUE;
	            }
	        });
	    count++;
		}
	}

}
