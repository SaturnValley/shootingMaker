# shootingMaker
マ.リ.オ.メ.ー.カ.ーに触発されたシューティングゲームです．
# shootingMaker
マ.リ.オ.メ.ー.カ.ーに触発されたシューティングゲームです．  

## ゲームをプレイする場合
  shootingMakerディレクトリ内のshootingMaker.jarをダウンロードし，以下のディレクトリ構造に配置してください．
  Javaがインストールされており，jarファイルを実行できる環境であれば動作するはずです．

  親フォルダ
  |__ exstages    
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ (このパスに作成したステージが保存される)  
  |  
  |__ shootingMaker.jar  

## 制作意図
  プログラミング面での特徴は以下の２点
  * キャラクターの追加を簡単に行える
  * 異なる機能間の関係を疎にし，部分的な更新ができる
  詳細は後述

## 操作方法
* ゲームプレイ/テストプレイモード
キーボードのみ可能

| キー | 動作 |
|:-----------|:------------|
| 矢印上（↑）/ w（ダブリュー）  |  上に移動      |
| 矢印左（←）/ a（エー）        |     左に移動   |
| 矢印下（↓）/ s（エス）        |      下に移動  |
| 矢印右（→）/ d（ディー）      | 右に移動       |
| スペース                      | 自機の弾の発射 |
| r（アール）                   |       レーダーの表示 |
| j（ジェー）                   |     自爆（ゲームオーバーに遷移） |
| c（シー）                     |     当たり判定の表示  （デバッグ用，プログラム内で有効無効を指定） |

* ステージの作成
マウス操作のみ可能

| マウス     |	場所        |	動作        |
|:-----------|:------------|:------------|
| 左クリック	| マップ上（画面左側）            |	キャラクターの設置                        |
|    〃      |	マップの端（画面左側の端1マス分）|	マップスクロール 長押しで多めにスクロール |
|	    〃     | キャラクターのアイコン           |	設置するキャラクター選択                  |
|	   〃      | タブ	                          | タブの切り替え                             |
|	    〃     | 各種ボタン                      |	ボタン名にある動作                        |
| 右クリック |	マップ上（画面左側）	          | 設置したキャラクターの削除                 |

* その他
マウス，キーボード操作ともに可能

| 操作 |	場所 |	動作 |
|:-----------|:------------|:------------|
| 矢印上（↑）/ w（ダブリュー）	| メニューのある画面	| メニューのスクロール | 
| 矢印下（↓）/ s（エス）      	| 	  〃              |         〃           | 
| 左クリック	                  | ステージ選択画面の矢印アイコン	|    〃     |                  
| スペース                     | メニューアイコン	   |        メニュー選択 | 
| 左クリック	                  |      〃             |         〃           |  
| t(ティー)	                  | モード選択，ステージ選択  | タイトルに戻る | 

## 各クラスの説明
各クラスは以下のようなパッケージ構造となっている

  |__ src  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ manager（ゲーム全体を管理する，各パッケージ間の連携やモードの遷移等）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ method（プログラム全体の固定値やファイルのI/Oを管理）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ objOnMap（ゲーム画面に表示するキャラクターの管理）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ body（自機と敵）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ jiki（自機）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ enemy（敵）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ bullet（弾）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ panel（ゲーム画面の各モードの表示や処理）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ gameClear（ゲームクリア画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ gameOver（ゲームオーバー画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ makerGUI（ステージ作成画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ modeSelect（ゲームモード選択画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ playGame（ゲームプレイ中の画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ stageSelect（ステージ選択画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ titke（タイトル画面）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ shootingMaker（プログラムのメイン）  
  |   
  |__ data  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ stages（ここ以下にプリセットのステージデータ）  
  |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|__ images（（ここ以下に画像データ）  
  |__ exstages(ここ以下に作成したステージが保存される)    

## キャラクターの追加（プログラムに加筆必要）
  以下の手順でキャラクターの追加が可能
  1. Bodyクラス/Jikiクラス/Bulletクラスを継承した新たなクラスをそれぞれ下位のパッケージに作成
  2. Factoryへ作成したクラスのコンストラクタを登録
  3. data/imagesに使用する画像の追加（必要に応じて）

## 実装されているキャラクター

* 横幅1マスの自機

| 名前	| 画像	| 動き	| 弾	| 備考| 
|:-----------|:------------|:------------|:------------|:------------|
| Aarwin	 | https://github.com/SaturnValley/shootingMaker/blob/master/data/images/arwin.png?raw=true　| 	慣性のある加速型	 | 上方向に飛ぶ四角弾	| 上級者向け| 
| Goddess	 | 　https://github.com/SaturnValley/shootingMaker/blob/master/data/images/goddess.png?raw=true　| 	慣性なしの等速型	 | 上方向に飛ぶ四角弾	| 初心者向け| 

* 横幅1マスの敵

| 名前	| 画像	| 動き	| 弾	| 備考| 
|:-----------|:------------|:------------|:------------|:------------|
| EmenyBlackD	|  https://github.com/SaturnValley/shootingMaker/blob/master/data/images/d.png?raw=true　	| なし	| 花火のように一定期間ではじける弾	| なし| 
| EmenyBlackO	 |　https://github.com/SaturnValley/shootingMaker/blob/master/data/images/o.png?raw=true 	| 円運動	| 自機狙い8方向弾	| なし| 
| EmenyBlackT	 | https://github.com/SaturnValley/shootingMaker/blob/master/data/images/t.png?raw=true	| T字型で往復	| 自機狙い弾3連射	| なし| 
| EmenyBlackU	 | https://github.com/SaturnValley/shootingMaker/blob/master/data/images/u.png?raw=true	| U字型で往復	| ３Way自機狙い弾	| なし| 
| EmenyBlackR	 | https://github.com/SaturnValley/shootingMaker/blob/master/data/images/r.png?raw=true	| 階段状で往復	| X軸方向にくっついたり離れたりする自機狙い弾	| なし| 
| EmenyYellowO	| https://github.com/SaturnValley/shootingMaker/blob/master/data/images/yo.png?raw=true |  	早くて大きい円運動	| ８Wayの自機狙い弾と偶数弾（自機外し）を交互	| HP3| 

* 横幅3マスの敵

| 名前	| 画像	| 動き	| 弾	| 備考| 
|:-----------|:------------|:------------|:------------|:------------|
| EnemySmallFace	 https://github.com/SaturnValley/shootingMaker/blob/master/data/images/face.png?raw=true| 	動かない	| 自機狙い弾 | 発射間隔1秒	| HP10画像はEnemyBigFaceの使いまわし| 

* 横幅8マスの敵

| 名前	| 画像	| 動き	| 弾	| 備考| 
|:-----------|:------------|:------------|:------------|:------------|
| EnemyBigBee	 | https://github.com/SaturnValley/shootingMaker/blob/master/data/images/bee.png?raw=true　| 	横向きの8の字型	| 8方向自機狙い弾を3連射	HP30| 
| EnemyLeftHand	 | https://github.com/SaturnValley/shootingMaker/blob/master/data/images/lefthand.png?raw=true　| 	円運動 自機が近づくとその位置に突進して戻る	| なし	HP15| 
| EnemyRightHand	 | 　https://github.com/SaturnValley/shootingMaker/blob/master/data/images/righthand.png?raw=true　| 	円運動 自機が近づくとその位置に突進して戻る	| なし	HP15| 
| EnemyBigFace	|　https://github.com/SaturnValley/shootingMaker/blob/master/data/images/face.png?raw=true |  	動かない	| 両手がない場合自機狙いの大きな弾を連射	| HP30EnemyLeftHandとEnemyRightHandを保持<bt>両手の有無で行動が変化<br>本体のHPが0になると両手のHPも0になる| 
