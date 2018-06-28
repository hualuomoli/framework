package com.github.hualuomoli.demo.security;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.hualuomoli.commons.codec.Base64;
import com.github.hualuomoli.demo.R;
import com.github.hualuomoli.security.AES;
import com.github.hualuomoli.security.RSA;

public class SecurityActivity extends AppCompatActivity {

    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJ68tWwKpcoiKYjDCRQXZp9aAvqmhixSyQfA0rHdiRxkqIZS5xiClyGr42w8fDSuh//chSTCzoxSDUJuXgDCLOVwQ4D+akLvLwuzuR0OeCnLAnPIT08KMH+GC+WN6Bgavoa0qWXYjzFPxvq4+8TU2UtR90DughpXysToKj2iCpPxbNUNA8k7BzDaVHPSUJCQNH4KWKJJSSjIKNOPZ/2ROuR5Ta6b5Xr8Vqjzyo3jN137oMOKyCZCaAWwQaYx9ZOcJiCaI1dQZrQFvPEvEFNEMbjHLrtuWbS62333z5ipGdrX7BIbniqd9wNNknJ7uejlb5FiYeY/h76r4BR76eT8NzAgMBAAECggEAaJaY0ia0ih6hoZDd26kKxPtxB/RvyEbBrqeDzOiLLLB0IM6p04pv84l4KUt6FwydF0I6QL5nZ/TlGjyNIcPSOSsflevo+SRpMkipub/zb8amhnqMULqSxx6BLpdx1D+jP1Ltcu1nRv7pFz3AeAkfBd/8BCSxv6Ig0wGOa/ajwfNp6C4ST49e2zNfAJ89jd8L8IAypRHycJ4+EmydpWTGsEG1k7+gZZpcK50Oi7t9QOuhucKceodsQN5/cMASsNhdaBx8B1eNqL1b/Zqre8EwUf+yHIFgidLjq2ib+pVSyXwGvwvrFnc1XC7QB1V31tju9ayWLjtwyoOvc1YDtTf64QKBgQDKp/9TxQoA7R39hCmZSKFtdDLEcKcr1ofkuaXIeI5LUWCFY0Dkm/dGpLkQJtoIUPB+KmfSKBiGpxj4o7tPKeM68iOsLBGj8SVquHu/dnQ8v1OWWkzjGy2e+grt7bsBRUpSkT4Km6nKaU4au98UCLTDaSAwbH2rEkPJwhtOARd2WQKBgQCuOZ0iBo4SfmTNBnOfoQ/qD2kq+GVlq/lU/yESbcQkeENjyz7hjoAU1cx6iGntyDhgWugNZPVulPiRVaEMK4fykUcutcmuPO+I4vZ2hL5AokGuAuAJeAh6Hr3tjXfIJfQs3Ip8wNU5zHn+EMLAJOxZGrlKaoxhGbmckc9841amqwKBgFoDFnhpu1DAASMacbOgu/q2stnnw4jIJ+Hix4zsZx9zDLESbb9cuw9ImN5gs1UgRkFzqluA7avZSlEdwFOgIkqLRGbzx/4OXPUy7dwTy/sza/gLMb1p0/CZ1QLrJbWhxio4Uts5WsgJz+Q18Bag61EkP2Tiu0qhuTRDIAecmoQ5AoGARsJdgCk7YGV3GRHqucVXSk+R/gK+Y8mxzQSFcQ+lns49SHWOh1sxx8SBdK6cnxc3VmL1ZiM/zvav8Y7ehqvTXgAE8gKg86QKAQV05fPJtURw+nRue0oF31tG9gkDwgUwI1w63qoOr5fNYBN8lT8pwpKo+IFHnFQvX1iU9Xr7gVsCgYEAiqKvnZOaDsv78yXs7gx7vdHcklGgec4ViO2S4SnnHHodntqWrgOZq7nIJbIio82kVA6WwlgTxdumMGpaCVEvvqXHM0C7nFfJ+Be3ANf3Bt4G2SmyqiUySni67GavFXh/JTObBD3S+QvcdF2KRRynibiiaSQVRZq3guje7bVLOlg=";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAievLVsCqXKIimIwwkUF2afWgL6poYsUskHwNKx3YkcZKiGUucYgpchq+NsPHw0rof/3IUkws6MUg1Cbl4AwizlcEOA/mpC7y8Ls7kdDngpywJzyE9PCjB/hgvljegYGr6GtKll2I8xT8b6uPvE1NlLUfdA7oIaV8rE6Co9ogqT8WzVDQPJOwcw2lRz0lCQkDR+CliiSUkoyCjTj2f9kTrkeU2um+V6/Fao88qN4zdd+6DDisgmQmgFsEGmMfWTnCYgmiNXUGa0BbzxLxBTRDG4xy67blm0utt998+YqRna1+wSG54qnfcDTZJye7no5W+RYmHmP4e+q+AUe+nk/DcwIDAQAB";
    private static final String KEY = Base64.encode("1234567890123456".getBytes());

    private Button signButton;
    private Button verifyButton;
    private Button encryptButton;
    private Button decryptButton;

    private EditText content;
    private EditText signatureContent;
    private EditText cipherContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        signButton = (Button) findViewById(R.id.security_button_sign);
        verifyButton = (Button) findViewById(R.id.security_button_verify);
        encryptButton = (Button) findViewById(R.id.security_button_encrypt);
        decryptButton = (Button) findViewById(R.id.security_button_decrypt);

        content = (EditText) findViewById(R.id.security_content);

        signatureContent = (EditText) findViewById(R.id.security_signature_content);
        cipherContent = (EditText) findViewById(R.id.security_cipher_content);

        // 签名
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureContent.setText(RSA.sign(PRIVATE_KEY, content.getText().toString()));
            }
        });

        // 验证
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = RSA.verify(PUBLIC_KEY, content.getText().toString(), signatureContent.getText().toString());
                Toast.makeText(SecurityActivity.this, "验证结果" + success, Toast.LENGTH_SHORT).show();
            }
        });

        // 加密
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cipherContent.setText(AES.encrypt(KEY, content.getText().toString()));
            }
        });

        // 解密
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = AES.decrypt(KEY, cipherContent.getText().toString());
                Toast.makeText(SecurityActivity.this, "验证结果=" + (data.equals(content.getText().toString())), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
