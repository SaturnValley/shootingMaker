package method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoadFile {

	public static String[] loadFileNames(String path, String jarName) {//ステージ名とか画像の名前を取得，拡張子を含む
		//同じディレクトリ内のファイルをとって，ファイル名のString配列を返す
		//外部jarを使うときはjarNameにその名前が入る，""なら使わない
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);//実行場所へのパス
		String names = "";
		//System.out.println("LoadFile path "+path+"jarName "+jarName+" url"+url);
		if (url != null) {
			// URLをファイルオブジェクトに変換
			//System.out.println("LoadFile getclasses jar "+ url.getProtocol());
			if (url.getProtocol().equals("jar")) {//jarファイルの場合
				JarFile jarFile = null;
				String[] s = url.getPath().split(":");
				String p = s[s.length - 1].split("!")[0];//aa.jarの形を探している
				//System.out.println("LoadFIle jar "+p);
				File f = new File(p);
				try {
					jarFile = new JarFile(f);
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				Enumeration<JarEntry> entries = jarFile.entries();//Jarファイルのそのディレクトリにある内容物
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					//System.out.println("loadfile entry getclass "+entry.getName());
					String name = entry.getName();
					//パッケージ名から始まり，.classで終わるファイル名を取得
					if (!entry.isDirectory() && name.startsWith(path)) {//entryがファイルでpathからはじまるなら
						String[] str = entry.getName().split("/");
						//System.out.println("loadfile entry getclass "+str);
						names += str[str.length - 1] + Const.DIV_ATTR_SIGN;//セーブファイルの仕切り文字と同じ記号で仕切る，この文字はファイル名で使われないはず
					}
				}
				try {
					jarFile.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {//対象pathがjarの中にない場合
				if (jarName.equals(Const.NON_JAR)) {//jarで実行している場合，ディレクトリ名に日本語あっても平気なように
					File dir = new File(path);//ファイルの場所，相対パス
					//System.out.println("LoadFile loadFileNames not innner dir "+dir);
					if (dir != null && dir.list() != null) {
						return dir.list();
					}
				} else {//eclipseで実行している場合
					File dir = new File(url.getPath());
					//System.out.println("LoadFile loadFileNames dir "+dir);
					if (dir != null && dir.list() != null && dir.list().length > 0) {
						for (File f : dir.listFiles()) {
							//System.out.println("LoadFile loadFileNames f "+f);
							if (f.isFile()) {//ファイルの名前のみ返す，ディレクトりはいらない
								names += f.getName() + Const.DIV_ATTR_SIGN;
							}
						}
					}
				}
			}
		} else if (url == null && !jarName.equals(Const.NON_JAR)) {//外部jarの時
			//System.out.println("FloadzFile LoadFileNames "+jarName);
			File file = new File(Const.JAR_DIR + jarName);
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(file);
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			URI uri = file.toURI();
			URLClassLoader classLoader = null;
			try {
				classLoader = new URLClassLoader(new URL[] { uri.toURL() });
			} catch (MalformedURLException ex) {
				// TODO 自動生成された catch ブロック
				ex.printStackTrace();
			}
			// 現在のスレッドに、classLoaderを設定
			Thread.currentThread().setContextClassLoader(classLoader);
			for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
				JarEntry entry = enumeration.nextElement();
				if (entry.getName().startsWith(path)) {
					String[] n = entry.getName().split("\\/");
					names += n[n.length - 1] + Const.DIV_ATTR_SIGN;
				}
			}
		} else {//外部パスの時はjarName=""でurlはnull
			File dir = new File(path);//ファイルの場所
			//System.out.println("LoadFile loadFileNames not innner dir "+dir);
			if (dir != null && dir.list() != null) {
				return dir.list();
			}
		}
		//System.out.println("LoadFIle loadFileName "+names);
		if (names.equals("")) {
			return null;
		}
		String[] list = names.split("\\" + Const.DIV_ATTR_SIGN);
		//System.out.println("LoadFIle listLen "+path+list.length);
		return list;
	}

	public static String[][] loadStage2ObjManager(String stageName) {//StageNameは拡張子なし
		File f;
		String line = null;
		if (LoadFile.isPresetStageName(stageName)) {//プリセットのstageにある場合
			stageName = LoadFile.decodePresetStageNameJp2En(stageName);//日本語ステージ名を英語に
			URL url = LoadFile.class.getClassLoader().getResource(Const.stagePath + stageName + Const.stageExtention);
			String path;
			if (url != null && url.getProtocol().contentEquals("jar")) {//jarの時
				//System.out.println("loadStage2ObjManager jar url "+url);
				//System.out.println("loadStage2ObjManager jar rsrc "+LoadFile.class.getClassLoader().getResource(Const.stagePath+stageName+Const.stageExtention));

				InputStream is = LoadFile.class.getClassLoader()
						.getResourceAsStream(Const.stagePath + stageName + Const.stageExtention);

				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				try {
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
				} catch (IOException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
				line = sb.toString();
				try {
					br.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				if (!stageName.equals(Const.TEST_STAGE)) {//ここも実行環境によって変えたい，＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
					path = url.getPath();
					f = new File(path);
				} else {//テストなら外部ファイル参照
					f = new File(Const.stageExternalPath + stageName + Const.stageExtention);
				}
				//System.out.println("LoadFIle loadStage2ObjManager "+f.exists()+ " "+f.getPath());
				Scanner scanner = null;
				try {
					scanner = new Scanner(f);
					while (scanner.hasNextLine()) {
						line = scanner.nextLine();//元のファイルは一行のはず
					}
				} catch (FileNotFoundException ex) {
					return null;
				} finally {
					if (scanner != null) {
						scanner.close();
					}
				}
			}
		} else {
			File dir = new File(Const.stageExternalPath);
			if (!dir.exists()) {//外部ファイルがなかったら作る
				//System.out.println("external save dir dont exist");
				if (dir.mkdir()) {//作れなかったらfalse返す
					//System.out.println("external save dir cant create");
				}
			}
			f = new File(Const.stageExternalPath + stageName + Const.stageExtention);
			Scanner scanner = null;
			try {
				scanner = new Scanner(f);
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();//元のファイルは一行のはず
				}
			} catch (FileNotFoundException ex) {
				return null;
			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}
		}
		if (line == null) {
			return null;
		}
		//Stringの配列，振り分けは呼び出し側で，[{名前，x,y}{...}]
		String[][] objects2d = null;
		//System.out.println("data"+line);
		String[] objects1d = line.split("\\" + Const.DIV_OBJ_SIGN);//オブジェクト単位に区切る,"\\"はエスケープ文字，一応
		/*
		for(String o:objects1d) {
			System.out.println(o);
		}
		*/
		objects2d = new String[objects1d.length][Const.NUM_OBJ_ATTR];//一つのオブジェクトは名前，x,y座標の3要素からなる
		int count = 0;
		for (String s : objects1d) {
			String[] attr = s.split("\\" + Const.DIV_ATTR_SIGN);//\\"はエスケープ文字，？には必要，変えたら消してもよいかも
			/*
			for(String o:attr) {
				System.out.println(o);
			}
			*/
			if (attr.length == Const.NUM_OBJ_ATTR) {
				for (int i = 0; i < Const.NUM_OBJ_ATTR; i++) {
					objects2d[count][i] = attr[i];
				}
			} else {//もし要素数が違ったらとりあえず0に格納
				objects2d[count][0] = s;
			}
			count++;
		}
		/*
		if(false) {//ここでチェックサムとか計算したい
			return null;//チェックサム通らない場合
		}
		*/
		return objects2d;
	}

	/*
	private static boolean checkSum(String checksum, String[][] obj2d) {
		String createCheckSum=obj2d.length+":";
		for(String[] array:obj2d) {
		}
		return false;
	}

	private static String calcCheckSum(OnMap[][] om) {
		return "";
	}
	*/

	//パッケージ名に当てはまる下位のクラスを取得
	public static List<Class<?>> getClasses(String packageName, String jarName)
			throws IOException, URISyntaxException, ClassNotFoundException {
		Enumeration<URL> e = null;
		List<Class<?>> classes = new ArrayList<>();
		//System.out.println("LoadFile getclasses  package " + packageName);
		try {
			e = Thread.currentThread().getContextClassLoader().getResources(packageName);
			//			e = ShootingMaker.class.getClassLoader().getResources(packageName);
		} catch (IOException ex) {
			// TODO 自動生成された catch ブロック
			ex.printStackTrace();
		}
		//System.out.println("LoadFile getclasses e " + e + e.hasMoreElements());
		if (e != null) {
			if (e.hasMoreElements()) {
				for (; e.hasMoreElements();) {
					// リソースのURLを取得
					URL url = e.nextElement();
					// URLをファイルオブジェクトに変換
					//System.out.println("LoadFile getclasses jar " + url.getProtocol());
					if (url.getProtocol().equals("jar")) {//jarファイルの場合
						JarFile jarFile;
						String[] s = url.getPath().split(":");
						String path = s[s.length - 1].split("!")[0];//aa.jarの形を探している
						//System.out.println("LoadFIle jar " + path);
						File f = new File(path);
						jarFile = new JarFile(f);
						Enumeration<JarEntry> entries = jarFile.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							//System.out.println("loadfile entry getclass " + entry.getName());
							String name = entry.getName();
							//パッケージ名から始まり，.classで終わるファイル名を取得
							if (name.startsWith(packageName) && name.endsWith(".class")) {
								//System.out.println(name.replace("/", ".").substring(0, name.length() - 6));//拡張子.classを抜く
								classes.add(Class.forName(name.replace("/", ".").substring(0, name.length() - 6)));
							}
						}
						jarFile.close();
					} else {//jarじゃない場合
						File dir = new File(url.getPath());
						//System.out.println("LoadFile getclasses dir " + dir);
						//URL u=LoadFile.class.getClassLoader().getResource(Const.imagePath+Const.TITLE_IMAGE);
						// ディレクトリ配下のファイル数分ループ
						if (dir != null) {
							for (String path : dir.list()) {
								// ".class"で終わるファイルのみ返却用のリストに追加
								if (path.endsWith(".class")) {
									//System.out.println(packageName.replace("/", ".") + "." + path.substring(0, path.length() - 6));
									classes.add(Class.forName(
											packageName.replace("/", ".") + "."
													+ path.substring(0, path.length() - 6)));
								}
							}
						}
					}
				}
			} else {//URLがない場合は外部JAR使っているときにおこる
				if (!jarName.equals(Const.NON_JAR)) {//jarにした時外部ファイルのディレクトリ構造は分からないみたい
					File file = new File(Const.JAR_DIR + jarName);
					JarFile jarFile = null;
					try {
						jarFile = new JarFile(file);
					} catch (IOException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
					URI uri = file.toURI();
					URLClassLoader classLoader = null;
					try {
						classLoader = new URLClassLoader(new URL[] { uri.toURL() });
					} catch (MalformedURLException ex) {
						// TODO 自動生成された catch ブロック
						ex.printStackTrace();
					}
					// 現在のスレッドに、classLoaderを設定
					Thread.currentThread().setContextClassLoader(classLoader);

					for (Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration
							.hasMoreElements();) {
						JarEntry entry = enumeration.nextElement();
						String name = entry.getName();
						if (name.startsWith(packageName) && name.endsWith(".class")) {
							// クラス名を変更
							String className = name.replaceAll("/", "\\.")
									.replaceAll("\\.class$", "");
							// クラスローダーに追加
							Class<?> targetClass = null;
							try {
								targetClass = classLoader.loadClass(className);
							} catch (ClassNotFoundException ex) {
								// TODO 自動生成された catch ブロック
								ex.printStackTrace();
							}
							classes.add(targetClass);
						}
					}
				}
			}
		}
		//System.out.println("LoadFile getclasses "+classes);

		return classes;
	}

	public static String splitClassName(String packageName) {//packageNameのパスからクラス名を返す
		//System.out.println("LoadFile splitClassName "+packageName);
		String packageList[] = packageName.split("\\.");
		//System.out.println("LoadFile splitClassName "+packageList.length);
		if (packageList.length > 0) {
			String className = packageList[packageList.length - 1];
			return className;
		}
		return null;
	}

	public static String[] pack2ClassName(String packageName, String jarName) {//packageNameに所属するクラスの名前を返す
		List<Class<?>> classes;
		String[] names = null;
		try {
			classes = LoadFile.getClasses(packageName, jarName);
			names = new String[classes.size()];
			int count = 0;
			for (Class<?> class1 : classes) {
				names[count] = splitClassName(class1.getName());//ここではちゃんとしたパッケージ名super.a.bの形になってるっぽい
				count++;
				//System.out.println("LoadFile pack2ClassName " + splitClassName(class1.getName()));
			}
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return names;
	}

	public static String[] stageExternalNames() {//外部ステージの名前を返す
		String[] stages = LoadFile.loadFileNames(Const.stageExternalPath, Const.NON_JAR);
		if (stages != null) {
			for (int i = 0; i < stages.length; i++) {
				//System.out.println("LoadFike stageExternalNames "+stages[i]);
				stages[i] = stages[i].split("\\" + Const.stageExtention)[0];
			}
		}
		return stages;
	}

	public static String[] stageAllNames() {//test.smstageを除く，ステージ名から拡張子を除いた配列を返す
		String[] stages = LoadFile.loadFileNames(Const.stagePath, Const.DATA_JAR_FILE);
		boolean isTest = false;
		//System.out.println("stages"+stages);
		if (stages != null) {//ここプリセットがないとExternalステージあってもnullが帰ってしまう
			for (int i = 0; i < stages.length; i++) {//ステージの名前だけにしている
				//拡張子を取り除く
				String stageName = stages[i].split("\\" + Const.stageExtention)[0];
				//プリセットにあるなら日本語名に
				stages[i] = stages[i] = LoadFile.decodePresetStageNameEn2Jp(stageName);
			}
			String[] stageExternalNames = LoadFile.stageExternalNames();
			//System.out.println("stageAllNames external len "+stageExternalNames);
			if (stageExternalNames != null) {
				for (int i = 0; i < stageExternalNames.length; i++) {
					if (stageExternalNames[i].equals("test")) {//テストは選択肢から外す
						stageExternalNames[i] = null;
						isTest = true;
					}
				}
				//System.out.println("LoadFile stageNamesRemovedTest"+stages[i]);
			}
			if (isTest) {//testを抜かした配列に
				String[] newStages = new String[stageExternalNames.length - 1];
				int count = 0;
				for (String s : stageExternalNames) {
					if (s != null) {//null以外をコピー
						newStages[count] = s;
						count++;
					}
				}
				stageExternalNames = newStages;
			}
			if (stages != null && stageExternalNames != null) {
				String[] allStageNames = new String[stages.length + stageExternalNames.length];
				System.arraycopy(stages, 0, allStageNames, 0, stages.length);//stagesをコピー
				System.arraycopy(stageExternalNames, 0, allStageNames, stages.length, stageExternalNames.length);//stageExternalをコピー
				stages = allStageNames;
			}
			//System.out.println("stageAllNames stages len "+stages.length);
			/*
			for (String s : stages) {
				System.out.println(s);
			}*/

			return stages;
		}
		return null;
	}

	public static boolean isPresetStageName(String stageName) {//ファイル名使えるかを判定,trueなら使えない
		//presetで使われる名前（英，日ともに）だったらtrue
		for (String[] ss : Const.STAGE_SAMPLE_JP) {
			for (String s : ss) {
				if (stageName.equals(s)) {
					return true;
				}
			}
		}
		//テストファイルと同じ名前，一部の記号，拡張子と同じ名前は使えない
		return stageName.equals(Const.TEST_STAGE) ||
				stageName.equals("") ||
				stageName.equals(Const.stageExtention) ||
				stageName.contains("/") ||
				stageName.contains(Const.DIV_ATTR_SIGN) ||
				stageName.contains(Const.DIV_OBJ_SIGN);
	}

	public static String decodePresetStageNameEn2Jp(String nameEn) {
		for (String[] s : Const.STAGE_SAMPLE_JP) {
			if (nameEn.equals(s[0])) {//英語版と等しい名前なら
				return s[1];
			}
		}
		return nameEn;
	}

	public static String decodePresetStageNameJp2En(String nameJp) {
		for (String[] s : Const.STAGE_SAMPLE_JP) {
			if (nameJp.equals(s[1])) {//英語版と等しい名前なら
				return s[0];
			}
		}
		return nameJp;
	}

}
