// Impor React dan useState untuk mengelola state input
import React, { useState } from 'react';

const OrderForm = ({ onSubmit }) => {
  // State untuk menyimpan data form order
  const [formData, setFormData] = useState({
    patientId: '',         // ID pasien (relasi ke tabel Patient)
    pharmacistId: '',      // ID apoteker (relasi ke tabel Pharmacist)
    prescriptionId: '',    // ID resep yang berisi daftar obat
    orderDate: '',         // Tanggal dan waktu pemesanan
    isPaid: false          // Status pembayaran
  });

  // Fungsi untuk menangani perubahan pada input form
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setFormData(prevState => ({
      ...prevState,
      [name]: type === 'checkbox' ? checked : value // handle checkbox vs text input
    }));
  };

  // Fungsi untuk submit form
  const handleSubmit = (e) => {
    e.preventDefault();

    // Lakukan parsing jika diperlukan (misalnya orderDate ke ISO string)
    const parsedData = {
      ...formData,
      orderDate: new Date(formData.orderDate).toISOString()
    };

    // Kirim data ke handler luar (props)
    onSubmit(parsedData);

    // Reset form setelah submit
    setFormData({
      patientId: '',
      pharmacistId: '',
      prescriptionId: '',
      orderDate: '',
      isPaid: false
    });
  };

  return (
    // Form UI
    <form onSubmit={handleSubmit} className="p-4 bg-white rounded shadow-md max-w-md mx-auto">
      <h2 className="text-xl font-bold mb-4">Form Pemesanan Obat</h2>

      {/* Input ID Pasien */}
      <label className="block mb-2">
        ID Pasien:
        <input
          type="text"
          name="patientId"
          value={formData.patientId}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Input ID Apoteker */}
      <label className="block mb-2">
        ID Apoteker:
        <input
          type="text"
          name="pharmacistId"
          value={formData.pharmacistId}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Input ID Resep */}
      <label className="block mb-2">
        ID Resep:
        <input
          type="text"
          name="prescriptionId"
          value={formData.prescriptionId}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Input Tanggal dan Jam Pemesanan */}
      <label className="block mb-2">
        Tanggal Pemesanan:
        <input
          type="datetime-local"
          name="orderDate"
          value={formData.orderDate}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Checkbox Status Pembayaran */}
      <label className="block mb-4">
        <input
          type="checkbox"
          name="isPaid"
          checked={formData.isPaid}
          onChange={handleChange}
          className="mr-2"
        />
        Sudah Dibayar?
      </label>

      {/* Tombol Submit */}
      <button type="submit" className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
        Simpan Order
      </button>
    </form>
  );
};

export default OrderForm;