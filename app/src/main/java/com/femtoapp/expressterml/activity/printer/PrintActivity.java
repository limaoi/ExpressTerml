package com.femtoapp.expressterml.activity.printer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;
import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpUtils;
import com.gprinter.command.LabelCommand;
import com.gprinter.io.GpDevice;
import com.gprinter.service.GpPrintService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Autism on 2018/2/3.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class PrintActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PrintActivity";
    private TextView tv_orderNumber_value;
    private TextView tv_sender_name_value;
    private TextView tv_phoneNumber_value;
    private TextView tv_sender_city_value;
    private TextView tv_sender_address_value;
    private TextView tv_receiver_name_value;
    private TextView tv_phoneNumber_value2;
    private TextView tv_receiver_city_value;
    private TextView tv_receiver_address_value;
    private TextView tv_goods_long_value;
    private TextView tv_goods_wide_value;
    private TextView tv_goods_high_value;
    private TextView tv_goods_weigth_value;
    private TextView tv_goods_money_value;
    private ImageView iv_back;
    private Button btn_commit;
    private TextView tv_connect;

    private String id;
    private String f_cFromPerson;
    private String f_cFromTel;
    private String f_cFromStation;
    private String f_cFromAddress;
    private String f_cFromAddress_port;
    private String f_cToPerson;
    private String f_cToTel;
    private String f_cToStation;
    private String f_cToAddress;
    private String f_cToAddress_port;
    private String f_cGoods;
    private String f_nPiece;
    private String f_nWeight;
    private String nlong;
    private String nwidth;
    private String nheight;
    private String f_nCubicMetre;
    private String f_cOrderNumber;
    private int status;
    private float f_nMoney;

    private String print_time;  //上传时间
    private GpService mGpService = null;
    private PrinterServiceConnection conn = null;
    public static final String CONNECT_STATUS = "connect.status";
    private int mTotalCopies = 0;
    private static final int MAIN_QUERY_PRINTER_STATUS = 0xfe;
    private int mPrinterIndex = 0;

    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        initView();

        initEvent();

        connection();

        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_DEVICE_REAL_STATUS));
    }


    private void initView() {
        tv_orderNumber_value = (TextView) findViewById(R.id.tv_orderNumber_value);
        tv_sender_name_value = (TextView) findViewById(R.id.tv_sender_name_value);
        tv_phoneNumber_value = (TextView) findViewById(R.id.tv_phoneNumber_value);
        tv_sender_city_value = (TextView) findViewById(R.id.tv_sender_city_value);
        tv_sender_address_value = (TextView) findViewById(R.id.tv_sender_address_value);
        tv_receiver_name_value = (TextView) findViewById(R.id.tv_receiver_name_value);
        tv_phoneNumber_value2 = (TextView) findViewById(R.id.tv_phoneNumber_value2);
        tv_receiver_city_value = (TextView) findViewById(R.id.tv_receiver_city_value);
        tv_receiver_address_value = (TextView) findViewById(R.id.tv_receiver_address_value);
        tv_goods_long_value = (TextView) findViewById(R.id.tv_goods_long_value);
        tv_goods_wide_value = (TextView) findViewById(R.id.tv_goods_wide_value);
        tv_goods_high_value = (TextView) findViewById(R.id.tv_goods_high_value);
        tv_goods_weigth_value = (TextView) findViewById(R.id.tv_goods_weigth_value);
        tv_goods_money_value = (TextView) findViewById(R.id.tv_goods_money_value);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        tv_connect = (TextView) findViewById(R.id.tv_connect);
    }


    private void initEvent() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        f_cFromPerson = intent.getStringExtra("f_cFromPerson");
        f_cFromTel = intent.getStringExtra("f_cFromTel");
        f_cFromStation = intent.getStringExtra("f_cFromStation");
        f_cFromAddress = intent.getStringExtra("f_cFromAddress");
        f_cFromAddress_port = intent.getStringExtra("f_cFromAddress_port");
        f_cToPerson = intent.getStringExtra("f_cToPerson");
        f_cToTel = intent.getStringExtra("f_cToTel");
        f_cToStation = intent.getStringExtra("f_cToStation");
        f_cToAddress = intent.getStringExtra("f_cToAddress");
        f_cToAddress_port = intent.getStringExtra("f_cToAddress_port");
        f_cGoods = intent.getStringExtra("f_cGoods");
        f_nPiece = intent.getStringExtra("f_nPiece");
        f_nWeight = intent.getStringExtra("f_nWeight");
        nlong = intent.getStringExtra("nlong");
        nwidth = intent.getStringExtra("nwidth");
        nheight = intent.getStringExtra("nheight");
        f_nCubicMetre = intent.getStringExtra("f_nCubicMetre");
        f_cOrderNumber = intent.getStringExtra("f_cOrderNumber");
        status = intent.getIntExtra("status", 0);
        f_nMoney = intent.getFloatExtra("f_nMoney", 0);

        tv_orderNumber_value.setText(f_cOrderNumber);
        tv_sender_name_value.setText(f_cFromPerson);
        tv_phoneNumber_value.setText(f_cFromTel);
        tv_sender_city_value.setText(f_cFromStation);
        tv_sender_address_value.setText(f_cFromAddress);
        tv_receiver_name_value.setText(f_cToPerson);
        tv_phoneNumber_value2.setText(f_cToTel);
        tv_receiver_city_value.setText(f_cToStation);
        tv_receiver_address_value.setText(f_cToAddress);
        tv_goods_long_value.setText(nlong + "cm");
        tv_goods_wide_value.setText(nwidth + "cm");
        tv_goods_high_value.setText(nheight + "cm");
        tv_goods_weigth_value.setText(f_nWeight + "KG");
        tv_goods_money_value.setText(f_nMoney + "元");

        iv_back.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        tv_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_connect:
                openPortDialogueClicked();
                break;
            case R.id.btn_commit:
                getTime();
                printTestClicked();
                break;
        }
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // GpCom.ACTION_DEVICE_REAL_STATUS 为广播的IntentFilter
            if (action.equals(GpCom.ACTION_DEVICE_REAL_STATUS)) {

                // 业务逻辑的请求码，对应哪里查询做什么操作
                int requestCode = intent.getIntExtra(GpCom.EXTRA_PRINTER_REQUEST_CODE, -1);
                // 判断请求码，是则进行业务操作
                if (requestCode == MAIN_QUERY_PRINTER_STATUS) {

                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    String str;
                    if (status == GpCom.STATE_NO_ERR) {
                        str = "打印机正常";
                    } else {
                        str = "打印机 ";
                        if ((byte) (status & GpCom.STATE_OFFLINE) > 0) {
                            str += "脱机";
                        }
                        if ((byte) (status & GpCom.STATE_PAPER_ERR) > 0) {
                            str += "缺纸";
                        }
                        if ((byte) (status & GpCom.STATE_COVER_OPEN) > 0) {
                            str += "打印机开盖";
                        }
                        if ((byte) (status & GpCom.STATE_ERR_OCCURS) > 0) {
                            str += "打印机出错";
                        }
                        if ((byte) (status & GpCom.STATE_TIMES_OUT) > 0) {
                            str += "查询超时";
                        }
                    }
                    Toast.makeText(getApplicationContext(), "打印机：" + mPrinterIndex + " 状态：" + str, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    };

    private void connection() {
        conn = new PrinterServiceConnection();
        Intent intent = new Intent(this, GpPrintService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }

    public void openPortDialogueClicked() {
        if (mGpService == null) {
            Toast.makeText(this, "Print Service is not start, please check it", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "openPortConfigurationDialog ");
        Intent intent = new Intent(this, PrinterConnectDialog.class);
        boolean[] state = getConnectState();
        intent.putExtra(CONNECT_STATUS, state);
        this.startActivity(intent);
    }

    public boolean[] getConnectState() {
        boolean[] state = new boolean[GpPrintService.MAX_PRINTER_CNT];
        for (int i = 0; i < GpPrintService.MAX_PRINTER_CNT; i++) {
            state[i] = false;
        }
        for (int i = 0; i < GpPrintService.MAX_PRINTER_CNT; i++) {
            try {
                if (mGpService.getPrinterConnectStatus(i) == GpDevice.STATE_CONNECTED) {
                    state[i] = true;
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return state;
    }


    public void printTestClicked() {
        try {
            int type = mGpService.getPrinterCommandType(mPrinterIndex);
      /*      if (type == GpCom.ESC_COMMAND) {
                String str = "1";
                int copies = 0;
                if (!str.equals("")) {
                    copies = Integer.parseInt(str);
                }
                mTotalCopies += copies;
                for (int i = 0; i < copies; i++) {
                    sendReceipt();
                }
            }*/
            if (type == GpCom.LABEL_COMMAND) {
                sendLabel();
                mGpService.queryPrinterStatus(mPrinterIndex, 1000, MAIN_QUERY_PRINTER_STATUS);
                if (status == GpCom.STATE_NO_ERR) {
                } else {
                    Toast.makeText(getApplicationContext(), "打印机错误！", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void sendLabel() {
        LabelCommand tsc = new LabelCommand();
        tsc.addSize(70, 40); // 设置标签尺寸，按照实际尺寸设置
        tsc.addGap(0); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
        tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
        tsc.addReference(0, 0);// 设置原点坐标
        tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
        tsc.addCls();// 清除打印缓冲区
        // 绘制图片
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        tsc.addBitmap(20, 20, LabelCommand.BITMAP_MODE.OVERWRITE, b.getWidth(), b);
        // 绘制简体中文
        tsc.addText(50, 20, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                "客服：400-001-9918\n");
        tsc.addText(20, 50, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                "哈啰物流\n");
        tsc.addQRCode(20, 90, LabelCommand.EEC.LEVEL_L, 5, LabelCommand.ROTATION.ROTATION_0, "http://hl.honghehello.com/module/hello/order_print.php?id=" + f_cOrderNumber);
        tsc.addText(50, 100, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                "收件：" + f_cToPerson + "\n" + "电话：" + f_cToTel + "\n" + "地址：" + f_cToStation + f_cToAddress + "\n");
        tsc.addText(20, 150, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                f_cOrderNumber + "\n");
        tsc.addPrint(1, 1); // 打印标签
        tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
        tsc.addCashdrwer(LabelCommand.FOOT.F5, 255, 255);
        Vector<Byte> datas = tsc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String str = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rel;
        try {
            rel = mGpService.sendLabelCommand(mPrinterIndex, str);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    void sendReceipt() {
        EscCommand esc = new EscCommand();
        /* 打印文字 */
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);// 取消倍高倍宽
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        Bitmap b = BitmapFactory.decodeResource(getResources(),
                R.drawable.logo2);
        esc.addRastBitImage(b, b.getWidth(), 0); // 打印图片
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        esc.addText("哈啰物流\n"); // 打印文字

        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
        esc.addTurnEmphasizedModeOnOrOff(EscCommand.ENABLE.ON); //加粗
        esc.addText("客服：400-001-9918\n"); // 打印文字
        esc.addTurnEmphasizedModeOnOrOff(EscCommand.ENABLE.OFF);
        esc.addText(f_cOrderNumber + "\n"); // 打印文字
        esc.addPrintAndLineFeed();
         /*绝对位置 具体详细信息请查看GP58编程手册 */
        esc.addText("货物：" + f_cGoods + " " + f_nWeight + "公斤" + " " + f_nCubicMetre + "方" + " " + f_nPiece + "件\n");
        esc.addText("收件地：" + f_cToStation + f_cToAddress + "\n");
        esc.addText("收件人：" + f_cToPerson + " " + f_cToTel + "\n");
        esc.addText("时间：" + print_time + "\n");
        esc.addPrintAndLineFeed();

        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);// 设置打印左对齐
        esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31); // 设置纠错等级
        esc.addSelectSizeOfModuleForQRCode((byte) 4);// 设置 qrcode 模块大小
        esc.addStoreQRCodeData("http://hl.honghehello.com/module/hello/order_print.php?id=" + f_cOrderNumber);// 设置 qrcode 内容
        esc.addPrintQRCode();// 打印 QRCode

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = GpUtils.ByteTo_byte(datas);
        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rs;
        try {
            rs = mGpService.sendEscCommand(mPrinterIndex, sss);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
            if (r != GpCom.ERROR_CODE.SUCCESS) {
                Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        print_time = formatter.format(curDate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn); // unBindService
        }
        unregisterReceiver(mBroadcastReceiver);
    }
}
