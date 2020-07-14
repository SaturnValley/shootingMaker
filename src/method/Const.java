package method;


//いろんなとこで使う定数をまとめる
public class Const {
	//マップのサイズ関係
	public final static int showWidth = (int)(640*2);//ゲーム画面の幅
	public final static int showHeight = (int)(480*2);
	public final static int isShowWidth = (int)(showWidth*1.5);//オブジェクトを表示する幅
	public final static int isShowHeight = (int)(showHeight*1.5);
	public final static int centerX = showWidth / 2;
	public final static int centerY = showHeight / 2;
	public final static int MapX=1920 * 4;
	public final static int MapY=1200 * 4;
	public final static int oneCell=32;
	public static final int SELECT_PANEL_WIDTH=16;

	//パッケージ名関係
	public final static String imagePath="images/";
	public final static String stagePath="stages/";
	public final static String stageExternalPath="exstages/";//外部のステージファイルの位置，stagePathとは別の名前にしたほうがわかりやすそう
	public static final String ENEMY_PK_NAME = "objOnMap/body/enemy";//Enemyのパッケージ名，ディレクトリ表記
	public static final String JIKI_PK_NAME = "objOnMap/body/jiki";
	public static final String JAR_DIR="shootingMaker_lib/";

	//調整用の固定値
	public static final String DIV_ATTR_SIGN = "?";//ステージファイルで同じオブジェクトの要素を分ける
	public static final String DIV_OBJ_SIGN = "!";//ステージファイルでオブジェクトを分ける
	public static final int NUM_OBJ_ATTR = 3;//ステージファイルでの1オブジェクトが持つ要素数，3だと名前，x,y
	public static final int ALPHA =0;//4byteで透明
	public static final int ALPHA_RED=-2130771968;//4byteで半透明の赤
	public static final int NUM_SHOW_SATGE=5;//ステージセレクトで同時に表示するステージ数

	//ファイル名関係
	public static final String TITLE_IMAGE="title.png";//title path
	public static final String DUMMY_IMAGE="dummy.png";
	public static final String TYPE_ENEMY="Enemy";//マップ作製の時に置くオブジェクトのタイプ
	public static final String TYPE_JIKI="Jiki";//マップ作製の時に置くオブジェクトのタイプ
	public static final String TEST_STAGE="test";
	public static final String DATA_JAR_FILE="data.jar";
	public static final String OBJONMAP_JAR_FILE="objOnMap.jar";
	public static final String NON_JAR="";//JARファイルを使わないときの定数
	public final static String stageExtention=".smstage";
	public final static String iconExtention=".png";

	//jarにすると日本語やスペースは文字化けする，プリセットのステージデータはここで日本語名を登録しとく
	public static String[][] STAGE_SAMPLE_JP= {
			{"Sample1ManyZako","サンプル1　雑魚たくさん"},
			{"Sample2VSBee","サンプル2　VS　大蜂"},
			{"Sample3VSOjisan","サンプル3　VS　顔おじさん"},
			{"Sample4ManyObject", "サンプル4　たくさん置いてみたかった"},
			{"Sample5Survival", "サンプル5　生存競争"},
			{"Sample6longlong", "サンプル6　縦長"}
	};


}
